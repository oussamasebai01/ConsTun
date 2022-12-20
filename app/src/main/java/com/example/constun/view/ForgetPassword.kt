package com.example.constun.view

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

class ForgetPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        val email = findViewById<EditText>(R.id.email)
        findViewById<Button>(R.id.forget).setOnClickListener {

            val apiInterface = ApiInterface.create()

            apiInterface.ForgetPassword(email.text.toString()).enqueue(object :
                Callback<User> {


                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(this@ForgetPassword, "Connexion error!", Toast.LENGTH_SHORT).show()

                }



                override fun onResponse(call: Call<User>, response: Response<User>) {



                    val user = response.body()
                    if (user != null) {
                        Toast.makeText(this@ForgetPassword, "Login Success", Toast.LENGTH_SHORT).show()
                        navigate()

                    } else {
                        Toast.makeText(this@ForgetPassword, "User not found", Toast.LENGTH_SHORT).show()
                    }
                }

            })
        }
    }

    private fun navigate() {
       val intent = Intent(this,ResetPassword::class.java)
        startActivity(intent)
    }
}