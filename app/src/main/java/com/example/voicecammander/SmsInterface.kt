package com.example.voicecammander

interface SmsInterface {
    fun onItemClick(
        Position: Int,
        SMSLIST: ArrayList<IteamLayout>
    )

    fun onLongClicked(
        Position: Int,
        SMSLIST: ArrayList<IteamLayout>
    )
}