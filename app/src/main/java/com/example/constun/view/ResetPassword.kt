package com.example.constun.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.constun.R
import com.example.constun.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.lolretrofit.utils.ApiInterface

class ResetPassword : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        val token = findViewById<EditText>(R.id.token)
        val newpassword = findViewById<EditText>(R.id.newpass)
        findViewById<Button>(R.id.reset).setOnClickListener {

            val apiInterface = ApiInterface.create()

            apiInterface.ResetPassword(token.text.toString(),newpassword.text.toString()).enqueue(object :
                Callback<User> {


                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(this@ResetPassword, "Connexion error!", Toast.LENGTH_SHORT).show()

                }



                override fun onResponse(call: Call<User>, response: Response<User>) {



                    val user = response.body()
                    if (user != null) {
                        Toast.makeText(this@ResetPassword, "Login Success", Toast.LENGTH_SHORT).show()
                        navigate()

                    } else {
                        Toast.makeText(this@ResetPassword, "User not found", Toast.LENGTH_SHORT).show()
                    }
                }

            })
        }
    }

    private fun navigate() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}