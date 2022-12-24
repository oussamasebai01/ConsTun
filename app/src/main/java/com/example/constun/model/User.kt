package com.example.constun.model

import androidx.room.ColumnInfo
import androidx.room.Entity

data class User(
    val _id :String,
    val email : String,
    val password : String,
    val numTel : String?,
    val matricule : String?,
    val code_assurence : String?,
    val cin : String?
)
