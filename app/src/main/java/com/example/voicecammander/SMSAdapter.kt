package com.example.voicecammander

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class SMSAdapter(
    val SMSLIST: ArrayList<IteamLayout>,
    var ctx: Context,
    var smsInterface: SmsInterface
) :
    RecyclerView.Adapter<SMSAdapter.SMSViewHolder>() {

    private var context = ctx

    class SMSViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageview: ImageView = itemView.findViewById(R.id.Avataer)
        val Pname: TextView = itemView.findViewById(R.id.pName)
        val Pnumber: TextView = itemView.findViewById(R.id.PNumber)
        val SMSmessage: TextView = itemView.findViewById(R.id.TextMAssege)
        val Date: TextView = itemView.findViewById(R.id.Date)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SMSViewHolder {

        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.smslist,parent,false)
        return SMSViewHolder(viewItem)
    }

    override fun getItemCount(): Int {

        return SMSLIST.size
    }

    override fun onBindViewHolder(holder: SMSViewHolder, position: Int) {

        val SMSPOSITION = SMSLIST[position]

        holder.Pname.text = SMSPOSITION.PName
        holder.Pnumber.text = SMSPOSITION.PNumber
        holder.SMSmessage.text = SMSPOSITION.SMSMESSEGE
        holder.Date.text = SMSPOSITION.DATE

        if (SMSLIST[position].imagesrc != null) {

            holder.imageview.setImageBitmap(SMSPOSITION.imagesrc)

        } else {
            holder.imageview.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.mipmap.ic_launcher_round
                )
            )
        }

        holder.itemView.setOnClickListener {
            smsInterface.onItemClick(position, SMSLIST)
        }

        holder.itemView.setOnLongClickListener {
            smsInterface.onLongClicked(position, SMSLIST)
            return@setOnLongClickListener true
        }
    }
}