package com.example.voicecammander

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.SmsMessage

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val extras = intent.extras
        if (extras != null) {
            val sms = extras.get("pdus") as Array<Any>

            for (i in sms.indices) {
                val format = extras.getString("format")
                var smsMessage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    SmsMessage.createFromPdu(sms[1] as ByteArray, format)
                } else {
                    SmsMessage.createFromPdu(sms[1] as ByteArray)
                }
                val phoneNumber = smsMessage.originatingAddress
                val messageText = smsMessage.messageBody.toString()
            }
        }
    }
}
