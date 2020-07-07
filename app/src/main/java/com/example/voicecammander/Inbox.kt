package com.example.voicecammander

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView

class Inbox : AppCompatActivity() {

    val REQUEST_CODE: Int = 3
    var permissions: Array<String> =
        arrayOf(
            Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS
        )
    var SMSLIST: ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inbox)

        val recycle = findViewById<RecyclerView>(R.id.SMSLISTVIEW)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
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
                        ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE)

                        //
                    })
                Aletbox.show()
            } else {

                //Show Alert Box To User

            }
        } else {
            //If Permission is Granted Write Code Here
        }


    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if ((grantResults.isEmpty()) && (grantResults[0] == PackageManager.PERMISSION_GRANTED) || (grantResults[1] == PackageManager.PERMISSION_GRANTED) || (grantResults[2] == PackageManager.PERMISSION_GRANTED)) {
            }
            val intent = Intent(this, Inbox::class.java)
            startActivity(intent)
        }
    }


}
