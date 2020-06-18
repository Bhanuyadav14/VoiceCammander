package com.example.voicecammander

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

@Suppress("UNREACHABLE_CODE")

class ContactAdapter(val ContactList:ArrayList<ContactLayout>, ctx:Context,var RecyclerInterface:RecyclerViewClickinterface) : RecyclerView.Adapter <ContactAdapter.ContactAdapterHolder>(){
    private var context = ctx
  inner  class ContactAdapterHolder (V:View):RecyclerView.ViewHolder(V){

        val imagesrc :ImageView = V.findViewById(R.id.PersonImage)
        val Name:TextView = V.findViewById(R.id.NamePerson)
        val Number:TextView = V.findViewById(R.id.personNumber)



  }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapterHolder {


        val viewItem:View = LayoutInflater.from(parent.context).inflate(R.layout.recycle,parent,false)
        return ContactAdapterHolder(viewItem)

    }

    override fun getItemCount(): Int {

       return  ContactList.size
    }

    override fun onBindViewHolder(holder: ContactAdapterHolder, position: Int) {


        val ContactPosition = ContactList[position]
        holder.Name.text=ContactPosition.Name
        holder.Number.text=ContactPosition.Number

       // holder.bind(ContactLayout(),position,clickListener = ContactAdapter.)

        if(ContactList[position].imagesrc!= null)
            holder.imagesrc.setImageBitmap(ContactPosition.imagesrc)
        else
            holder.imagesrc.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.ic_launcher_round))

        holder.itemView.setOnClickListener {

            RecyclerInterface.onItemClick(position,ContactList)
//            RecyclerInterface.onLongClicked(position)

         }

        holder.itemView.setOnLongClickListener {

             RecyclerInterface.onLongClicked(position,ContactList)
            return@setOnLongClickListener true
        }

    }


}