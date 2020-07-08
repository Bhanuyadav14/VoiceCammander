package com.example.voicecammander

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class Contact : AppCompatActivity() , RecyclerViewClickinterface,TextToSpeech.OnInitListener{
  lateinit var recycleView: RecyclerView
    lateinit var Recyclerinterface: RecyclerViewClickinterface


      var tts:TextToSpeech?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        tts = TextToSpeech(this, this)

        recycleView = findViewById<RecyclerView>(R.id.recycleview)

        recycleView.layoutManager = LinearLayoutManager(this)

        var Divider: DividerItemDecoration = DividerItemDecoration(this,DividerItemDecoration.VERTICAL)
        recycleView.addItemDecoration(Divider)



        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            val contactList: MutableList<ContactLayout> = ArrayList()
            val contacts: Cursor? = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                null
            )
            if (contacts != null) {
                while (contacts.moveToNext()) {
                    val name =
                        contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    val number =
                        contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    val obj = ContactLayout()
                    obj.Name = name
                    obj.Number = number

                    val photo_uri = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))
                    if(photo_uri != null){
                        obj.imagesrc = MediaStore.Images.Media.getBitmap(contentResolver,Uri.parse(photo_uri))
                    }
                    contactList.add(obj)
                }
            }
            recycleView.adapter = ContactAdapter(contactList as ArrayList<ContactLayout>,this,this)
            contacts!!.close()

        }
else
        {

            Toast.makeText(this, "Permision needed",Toast.LENGTH_SHORT).show()

        }


        }

    override fun onItemClick(
        Position: Int,
        contactList: ArrayList<ContactLayout>
    ) {
      val pos :Int=Position
       val Name:String=contactList[pos].Name
        val Number: String = contactList[pos].Number
        Toast.makeText(applicationContext,""+Name+"\n"+Number,Toast.LENGTH_LONG).show()
        speakOut(Name,Number)
    }

    override fun onLongClicked(
        Position: Int,
        contactList: ArrayList<ContactLayout>
    ) {
        val pos :Int=Position
        val Name:String = contactList[pos].Name
        val Number :String =contactList[pos].Number
        Toast.makeText(applicationContext,""+Name+"\n"+Number,Toast.LENGTH_LONG).show()
        MakeCall(Name,Number)
        speakOut1(Name,Number)
    }

    private fun MakeCall(name:String,number:String){
        val Name:String = name
        val Number:String = number
        try {


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.CALL_PHONE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {

                    val AlertBox: AlertDialog.Builder = AlertDialog.Builder(this)
                    AlertBox.setMessage("You Want To Make Call To This Person : $Name")
                    AlertBox.setPositiveButton(
                        "Call",
                        DialogInterface.OnClickListener { dialog, which ->

                            val callIntend: Intent =
                                Intent(Intent.ACTION_CALL, Uri.fromParts("tel", Number, null))
                            startActivity(callIntend)

                        })

                    AlertBox.show()

                    AlertBox.setNegativeButton(
                        "Cancel",
                        DialogInterface.OnClickListener { dialog, which ->


                        })
                    AlertBox.show()

                    AlertBox.setCancelable(false)
                    AlertBox.show()

                } else {

                    Toast.makeText(this, "The Permission Neede For making Call ", Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                val callIntend = Intent(Intent.ACTION_CALL)
                callIntend.data = Uri.parse(Number)
                startActivity(callIntend)
            }
        }
        catch (ex:Exception){
            ex.printStackTrace()
        }
    }


    override fun onInit(status: Int) {

        if(status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.ENGLISH)
        }

        else{
            Toast.makeText(this,"Somethig Goes Wrong ",Toast.LENGTH_SHORT).show()
        }



    }

    private fun speakOut(text1:String,text2:String) {
        val text:String = "The Name Of PerSon Is: $text1 and Number Is :$text2"

        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null,"")
    }

    public override fun onDestroy() {
        // Shutdown TTS
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }

    private fun speakOut1(text1: String,text2: String){
        val text:String = "You Want To Call This PerSon $text1 and the Number Of the person Is $text2"
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null,"")

    }

    fun Addcontact(view: View) {

        val intent = Intent(ContactsContract.Intents.Insert.ACTION).apply {
            type = ContactsContract.RawContacts.CONTENT_TYPE
        }

        startActivity(intent)

    }


}



