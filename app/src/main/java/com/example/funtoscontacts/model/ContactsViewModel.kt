package com.example.funtoscontacts.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.funtoscontacts.Contacts
import kotlinx.coroutines.launch

class ContactsViewModel(val repository: ContactsRepository) : ViewModel() {

    fun insertContact(contacts: Contacts){
        viewModelScope.launch {
            repository.upsert(contacts)
        }
    }


    fun deleteContact(contacts: Contacts){
        viewModelScope.launch {
            repository.delete(contacts)
        }
    }

    fun getContact() = repository.getContacts()

    fun getContactId(id : Long) = repository.getContactsId(id)

    fun searchContacts(searchQuery : String) = repository.searchContacts(searchQuery)


}