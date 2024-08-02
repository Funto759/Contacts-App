package com.example.funtoscontacts.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.funtoscontacts.Contacts

@Dao
interface ContactsDao {

    @Upsert
    suspend fun upsertContact(contacts: Contacts) : Long

    @Query("SELECT * FROM contacts ORDER BY firstName ASC")
    fun getContacts():LiveData<List<Contacts>>

    @Query("SELECT * FROM contacts WHERE id = :id")
    fun getContactId(id : Long): LiveData<Contacts>


    @Query("SELECT * FROM contacts WHERE firstName LIKE :searchQuery || '%' OR phoneNumber LIKE :searchQuery || '%'")
    fun searchContacts(searchQuery : String) : LiveData<List<Contacts>>


    @Delete
    suspend fun deleteContact(contacts: Contacts) : Int
}