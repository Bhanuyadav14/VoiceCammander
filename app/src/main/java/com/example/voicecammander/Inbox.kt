package com.example.voicecammander

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Inbox : AppCompatActivity(), SmsInterface, TextToSpeech.OnInitListener {

    lateinit var smsInterface: SmsInterface
    var tts: TextToSpeech? = null

    private var date: String = ""
    val REQUEST_CODE: Int = 3
    var permissions: Array<String> =
        arrayOf(
            Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS
        )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inbox)

        val recycle = findViewById<RecyclerView>(R.id.SMSLISTVIEW)
        var Divider: DividerItemDecoration =
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recycle.addItemDecoration(Divider)

        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.READ_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            if (shouldShowRequestPermissionRationale(

                    Manifest.permission.READ_SMS
                )
            ) {
                val Aletbox: AlertDialog.Builder = AlertDialog.Builder(
                    this
                )
//                   Aletbox.setTitle("Grant These Permission")
                Aletbox.setMessage("This Permission Requred for Reading Contacts And etc..")
                Aletbox.setNegativeButton("Deny", DialogInterface.OnClickListener { dialog, which ->

                    finish()
                })

                Aletbox.show()

                Aletbox.setPositiveButton(
                    "Allow",
                    DialogInterface.OnClickListener { dialog, which ->
                        ActivityCompat.requestPermissions(this@Inbox, permissions, REQUEST_CODE)

                        //
                    })
                Aletbox.show()
            } else {

                //Show Alert Box To User
                ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE)

            }
        } else {
            //If Permission is Granted Write Code Here
            val SMSLIST: MutableList<IteamLayout> = ArrayList()
            var cursor =
                contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null)
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    var name: String =
                        cursor.getString(cursor.getColumnIndexOrThrow("address")).toString()
                    val MSM: String =
                        cursor.getString(cursor.getColumnIndexOrThrow("body")).toString()

                    val timestamp: Long = cursor.getLong(cursor.getColumnIndex("date"))

                    val obj = IteamLayout()
                    obj.PNumber = name
                    obj.PName = GetContact().toString() //This Retuns PerSon Name

                    if (GetImage() != null) {
                        obj.imagesrc = GetImage()
                    }

                    obj.SMSMESSEGE = MSM
                    obj.DATE = GetDate(timestamp, "dd/MM/yyyy hh:mm:ss aa")
                    SMSLIST.add(obj)
                }
            }

            recycle.adapter = SMSAdapter(SMSLIST as ArrayList<IteamLayout>, this, this)
            cursor!!.close()


        }


    }




    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (grantResults.isEmpty() || (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            }
            val intent = Intent(this, Inbox::class.java)
            startActivity(intent)
        }
    }

    //Get the Contact
    private fun GetContact(): String? {

        return null

    }

    //Get Images Of Contact
    private fun GetImage(): Bitmap? {
        var AV: Bitmap? = null
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val images =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))

                if (images != null) {
                    AV = MediaStore.Images.Media.getBitmap(contentResolver, Uri.parse(images))
                }

            }
        }
        cursor?.close()
        return AV
    }

    override fun onItemClick(Position: Int, SMSLIST: ArrayList<IteamLayout>) {
        val pos: Int = Position
        val Name: String = SMSLIST[pos].PNumber
        val Message: String = SMSLIST[pos].SMSMESSEGE


        Toast.makeText(
            applicationContext,
            " " + speakOut(Name) + "\n" + speakOut1(Message),
            Toast.LENGTH_SHORT
        ).show()

    }

    //This Function
    override fun onLongClicked(Position: Int, SMSLIST: ArrayList<IteamLayout>) {
        Toast.makeText(applicationContext, "You Click Long $Position", Toast.LENGTH_SHORT).show()
    }

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.ENGLISH)
        } else {
            Toast.makeText(this, "Somethig Goes Wrong ", Toast.LENGTH_SHORT).show()
        }


    }

    //This Function Read The Message And PerSon Name
    private fun speakOut(text1: String) {
        val text: String = "The Name Of Person Is :" + text1

        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null)
    }

    public override fun onDestroy() {
        // Shutdown TTS
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }

    // This Function Give The Date Of Message
    private fun GetDate(Time: Long, dateFormate: String): String {
        var Formate: DateFormat = SimpleDateFormat(dateFormate)
        var calender: Calendar = Calendar.getInstance()
        calender.timeInMillis = Time
        return Formate.format(calender.time)

    }

    private fun speakOut1(message: String) {
        val text: String = "The Name Of Person Is :" + message

        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null)
    }

}
