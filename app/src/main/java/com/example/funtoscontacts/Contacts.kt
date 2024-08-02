package com.example.funtoscontacts

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "contacts")
data class Contacts(
                    val firstName : String,
                    val surname : String,
                    val phoneNumber:String,
                    val email:String,
                    val nickName : String,
                    @PrimaryKey(autoGenerate = true)
                    val id : Long = 0)  : Serializable
