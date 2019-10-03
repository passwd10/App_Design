package com.example.myapplication2

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.contact_item.view.*

class ContactAdapter : RecyclerView.Adapter<ContactAdapter.contactViewHolder>() {

    var contactItems : MutableList<ContactItem> = mutableListOf(
        ContactItem("김철수","010-1234-1234",false),
        ContactItem("박선규","010-1323-1235",false),
        ContactItem("이태원","010-5124-3455",false),
        ContactItem("강민건","010-5353-7653",false),
        ContactItem("최태웅","010-6542-1655",false)
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): contactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item,parent,false)
        return contactViewHolder(view)
    }

    override fun getItemCount(): Int = contactItems.size

    override fun onBindViewHolder(holder: contactViewHolder, position: Int) {
        holder.contactName.text = contactItems[position].contactName
        holder.contactNum.text = contactItems[position].contactNum
        holder.contactFavorite = contactItems[position].contactFavorite
    }

    class contactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var contactName : TextView = itemView.tv_contact_name
        var contactNum : TextView = itemView.tv_contact_num
        var contactFavorite : Boolean = itemView.cb_contact_favorite.isChecked

    }
}