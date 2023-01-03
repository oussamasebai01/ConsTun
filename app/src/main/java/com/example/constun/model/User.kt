package com.example.constun.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("_id")
    val _id :String? = null,
    @SerializedName("email")
    val email : String? = null,
    @SerializedName("password")
    val password : String? = null,
    @SerializedName("numTel")
    val numTel : String? = null,
    @SerializedName("matricule")
    val matricule : String? = null,
    @SerializedName("code_assurence")
    val code_assurence : String? = null,
    @SerializedName("cin")
    val cin : String? = null
)
