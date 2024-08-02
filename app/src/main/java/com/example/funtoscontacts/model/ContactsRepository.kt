package com.example.funtoscontacts.model

import com.example.funtoscontacts.Contacts

class ContactsRepository(val db:ContactsDatabase)  {

    suspend fun upsert(contacts: Contacts) = db.getContactsDao().upsertContact(contacts)

    suspend fun delete(contacts: Contacts) =  db.getContactsDao().deleteContact(contacts)

    fun getContacts() = db.getContactsDao().getContacts()

    fun searchContacts(searchQuery : String) = db.getContactsDao().searchContacts(searchQuery)

    fun getContactsId(id : Long) = db.getContactsDao().getContactId(id)
}