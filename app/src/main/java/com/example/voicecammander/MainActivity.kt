package com.example.voicecammander

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    val MyRequest1: Int = 1
    val MyRequest2: Int = 2
    val MyRequest3: Int = 3
    val permission: Array<String> = arrayOf(
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.WRITE_CONTACTS,
        Manifest.permission.READ_SMS,
        Manifest.permission.RECEIVE_SMS,
        Manifest.permission.SEND_SMS,
        Manifest.permission.CALL_PHONE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button1 = findViewById(R.id.contact)
        button2 = findViewById(R.id.inboxmsg)
        button3 = findViewById(R.id.BatteryInfo)

        ButtonClick()

    }

    private fun ButtonClick() {

        button1.setOnClickListener {
            CheckPermission(permission, "ContactReading", MyRequest1)
            val intent = Intent(this, Contact::class.java)
            startActivity(intent)
        }

        button2.setOnClickListener {
            CheckPermission(permission, "Inbox", MyRequest2)
            val intent = Intent(this, Inbox::class.java)
            startActivity(intent)
        }

        button3.setOnClickListener {
            CheckPermission(permission, "Battery", MyRequest3)
            val intent = Intent(this, Battery::class.java)
            startActivity(intent)
        }

    }

    private fun CheckPermission(permission: Array<String>, name: String, requestcode: Int) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            when {
                ContextCompat.checkSelfPermission(
                    applicationContext,
                    permission.toString()
                ) == PackageManager.PERMISSION_GRANTED -> {

                    Toast.makeText(
                        applicationContext,
                        "$name permission granted",
                        Toast.LENGTH_SHORT
                    ).show()

                }
                shouldShowRequestPermissionRationale(permission.toString()) -> showDialog(
                    permission,
                    name,
                    requestcode
                )

                else -> ActivityCompat.requestPermissions(this, permission, requestcode)
            }

        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        fun innercheck(name: String) {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(applicationContext, "$name Permission Refused", Toast.LENGTH_SHORT)
                    .show()

            } else {
                Toast.makeText(applicationContext, "$name Permission Granted", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        when (requestCode) {
            MyRequest1 -> innercheck("ContactReading")
            MyRequest2 -> innercheck("Inbox")
            MyRequest3 -> innercheck("Battery")
        }
    }

    private fun showDialog(permission: Array<String>, name: String, requestCode: Int) {

        val builder = AlertDialog.Builder(this)
        builder.apply {
            setMessage("Permission to Access your $name is required to use this app")
            setTitle("Permission Required")
            setPositiveButton("Allow") { dialog, which ->
                ActivityCompat.requestPermissions(this@MainActivity, permission, requestCode)
            }
        }

        val dialog = builder.create()
        dialog.show()

    }


}
