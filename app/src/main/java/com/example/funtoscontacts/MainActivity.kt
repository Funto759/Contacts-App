package com.example.funtoscontacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.funtoscontacts.databinding.ActivityMainBinding
import com.example.funtoscontacts.databinding.ContactsMainPageBinding
import com.example.funtoscontacts.model.ContactsDatabase
import com.example.funtoscontacts.model.ContactsRepository
import com.example.funtoscontacts.model.ContactsViewModel
import com.example.funtoscontacts.model.ContactsViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    private lateinit var navController : NavController
    lateinit var viewModel : ContactsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val contactsRepository = ContactsRepository(ContactsDatabase(this))
        val viewModelProviderFactory = ContactsViewModelProviderFactory(contactsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[ContactsViewModel::class.java]

        val navHost = supportFragmentManager.findFragmentById(R.id.contactsNavHostFragment) as  NavHostFragment
        navController = navHost.navController





    }
}