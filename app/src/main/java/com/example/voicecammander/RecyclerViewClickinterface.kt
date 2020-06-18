package com.example.voicecammander

interface RecyclerViewClickinterface {
    fun onItemClick(
        Position: Int,
        contactList: ArrayList<ContactLayout>
    )
    fun onLongClicked(
        Position: Int,
        contactList: ArrayList<ContactLayout>
    )

}