package com.example.funtoscontacts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.funtoscontacts.Contacts
import com.example.funtoscontacts.R

class ContactsAdapter : RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {

    inner class ContactsViewHolder(itemView : View): RecyclerView.ViewHolder(itemView)

    private val differCallBack = object  : DiffUtil.ItemCallback<Contacts>(){

        override fun areItemsTheSame(oldItem: Contacts, newItem: Contacts): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Contacts, newItem: Contacts): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        return ContactsViewHolder(LayoutInflater.from(parent.context).
        inflate(R.layout.item_contacts_view,parent,false))
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val contact = differ.currentList[position]
        holder.itemView.apply {
            var firstname = findViewById<TextView>(R.id.firstname)
            var number = findViewById<TextView>(R.id.phonenumber)
            var nickname = findViewById<TextView>(R.id.nickname)

            firstname.text = contact.firstName
            number.text = contact.phoneNumber
            nickname.text = contact.nickName

            setOnClickListener { onItemClickListener?.let { it(contact) } }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setOnItemClickListener(listener : (Contacts) -> Unit){
        onItemClickListener = listener
    }

    private var onItemClickListener : ((Contacts) -> Unit)? = null
}