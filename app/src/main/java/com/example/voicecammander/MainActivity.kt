package com.example.voicecammander

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    val MyRequest: Int = 4
    val permission: Array<String> = arrayOf(
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.WRITE_CONTACTS,
        Manifest.permission.READ_SMS,
        Manifest.permission.CALL_PHONE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button1 = findViewById(R.id.contact)
        button2 = findViewById(R.id.inboxmsg)
        button3 = findViewById(R.id.BatteryInfo)




        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            button1.setOnClickListener {
                val intent = Intent(this, Contact::class.java)
                startActivity(intent)
            }
            button2.setOnClickListener {
                val intent = Intent(this, Inbox::class.java)
                startActivity(intent)
            }
            button3.setOnClickListener {
                val intent = Intent(this, Battery::class.java)
                startActivity(intent)
            }
            Toast.makeText(this, "PERMISSION GRANTED", Toast.LENGTH_SHORT).show()
        } else {

            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_CONTACTS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.READ_CONTACTS
                    )
                ) {
                    val Aletbox: AlertDialog.Builder = AlertDialog.Builder(
                        this
                    )
//                   Aletbox.setTitle("Grant These Permission")
                    Aletbox.setMessage("This Permission Requred for Reading Contacts And etc..")
                    Aletbox.setNegativeButton(
                        "Deny",
                        DialogInterface.OnClickListener { dialog, which ->

                            Aletbox.show()
                        })
                    Aletbox.setPositiveButton(
                        "Allow",
                        DialogInterface.OnClickListener { dialog, which ->
                            ActivityCompat.requestPermissions(this, permission, MyRequest)

                            //
                        })
                    Aletbox.show()
                }
           else{

                   Toast.makeText(this, "This PERMISSION All Redy GRANTED",Toast.LENGTH_SHORT).show()
               }}
           else {
                   Toast.makeText(this, "This PERMISSION IS NOT GRANTED",Toast.LENGTH_SHORT).show()
               }
          // if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_CONTACTS)!=PackageManager.PERMISSION_GRANTED)


       }




    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == MyRequest){
            if ((grantResults.isNotEmpty()) && (grantResults[0] == PackageManager.PERMISSION_GRANTED )||(grantResults[1] == PackageManager.PERMISSION_GRANTED) ||(grantResults[2] == PackageManager.PERMISSION_GRANTED)||(grantResults[3]==PackageManager.PERMISSION_GRANTED)) {
                button1.setOnClickListener {
                    val intent = Intent(this, Contact::class.java)
                    startActivity(intent)
                }
                button2.setOnClickListener {
                    val intent = Intent(this, Inbox::class.java)
                    startActivity(intent)
                }
                button3.setOnClickListener {
                    val intent = Intent(this, Battery::class.java)
                    startActivity(intent)
                }
                Toast.makeText(this, "PERMISSION GRANTED", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "PERMISSION Not GRANTED",Toast.LENGTH_SHORT).show()
            }
        }
    }

}
