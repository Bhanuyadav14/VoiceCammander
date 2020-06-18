package com.example.voicecammander

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

class SMSAdapter(val SMSLIST: ArrayList<IteamLayout>) :RecyclerView.Adapter<SMSAdapter.SMSViewHolder>() {
    class SMSViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

        val imageview :ImageView =itemView.findViewById(R.id.Avataer)
        val Pname:TextView=itemView.findViewById(R.id.pName)
        val Pnumber : TextView = itemView.findViewById(R.id.PNumber)
        val SMSmessage :TextView = itemView.findViewById(R.id.TextMAssege)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SMSViewHolder {
        TODO("Not yet implemented")
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.smslist,parent,false)
        return SMSViewHolder(viewItem)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
        return SMSLIST.size
    }

    override fun onBindViewHolder(holder: SMSViewHolder, position: Int) {
        TODO("Not yet implemented")
        val SMSPOSITION = SMSLIST[position]
        holder.imageview.setImageResource(SMSPOSITION.imagesrc)
        holder.Pname.text=SMSPOSITION.PName
        holder.Pnumber.text=SMSPOSITION.PNumber
        holder.SMSmessage.text=SMSPOSITION.SMSMESSEGE
    }
}