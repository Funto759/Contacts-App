package com.example.funtoscontacts.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.funtoscontacts.Contacts

@Database(
    entities = [Contacts::class],
    version = 1,
    exportSchema = false
)
abstract class ContactsDatabase : RoomDatabase(){

    abstract fun getContactsDao():ContactsDao

    companion object {
        @Volatile
        private var instance: ContactsDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {

            instance?: getDatabase(context).also { instance = it }

        }

        private fun getDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, ContactsDatabase::class.java, "contacts_database"
        ).fallbackToDestructiveMigration().build()

    }
}