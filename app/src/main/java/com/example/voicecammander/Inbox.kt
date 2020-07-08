package com.example.voicecammander

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

class Inbox : AppCompatActivity(), SmsInterface {

    lateinit var smsInterface: SmsInterface

    private var date: String = ""
    val REQUEST_CODE: Int = 3
    var permissions: Array<String> =
        arrayOf(
            Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS
        )

    val SMSLIST: MutableList<IteamLayout> = ArrayList()

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
            var cursor =
                contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null)
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    var name: String =
                        cursor.getString(cursor.getColumnIndexOrThrow("address")).toString()
                    val MSM: String =
                        cursor.getString(cursor.getColumnIndexOrThrow("body")).toString()
                    //val date:String = cursor.getString(cursor.getColumnIndexOrThrow("date"))
                    //val id : String = cursor.getString(cursor.getColumnIndexOrThrow("id")).toString()

                    val timestamp: Long = cursor.getLong(cursor.getColumnIndex("date"))

                    val obj = IteamLayout()
                    obj.PNumber = name
                    // obj.DATE= setDate(getDate(timestamp,"dd/MM/yyyy hh:mm:ss aa")).toString()
                    obj.SMSMESSEGE = MSM
                    SMSLIST.add(obj)
                }
            }

            recycle.adapter = SMSAdapter(SMSLIST as ArrayList<IteamLayout>, this, this)
            cursor!!.close()


        }


    }

    private fun getDate(timestamp: Long, s: String): Any {
        return date
    }

    private fun setDate(date: Any) {
        this.date = date as String
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

    override fun onItemClick(Position: Int, SMSLIST: ArrayList<IteamLayout>) {
        Toast.makeText(applicationContext, "You Click $Position", Toast.LENGTH_SHORT).show()
    }

    override fun onLongClicked(Position: Int, SMSLIST: ArrayList<IteamLayout>) {
        Toast.makeText(applicationContext, "You Click Long $Position", Toast.LENGTH_SHORT).show()
    }


}
