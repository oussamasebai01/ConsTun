package com.example.constun.model

import androidx.room.ColumnInfo
import androidx.room.Entity

data class User(
    val _id :String,
    val email : String,
    val password : String

)
