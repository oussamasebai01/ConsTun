package com.example.constun.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.constun.R
import com.example.constun.model.User
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.lolretrofit.utils.ApiInterface

class LoginActivity : AppCompatActivity() {

    lateinit var txtUsername: TextInputLayout
    lateinit var txtLayoutUsername: TextInputEditText

    lateinit var txtPassword: TextInputLayout
    lateinit var txtLayoutPassword: TextInputEditText

    lateinit var btnLogin: Button

    lateinit var btnCreer: Button



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtUsername = findViewById(R.id.textusername)
        txtPassword = findViewById(R.id.textpassword)
        txtLayoutUsername = findViewById(R.id.editTextusername)
        txtLayoutPassword = findViewById(R.id.editTextpassword)



        btnLogin = findViewById(R.id.connect)
        btnLogin.setOnClickListener{

            doConnect()

            if(doConnect())
            {
                val intent = Intent(this,HomeActivity::class.java)
                startActivity(intent)
            }
        }

        btnCreer = findViewById(R.id.Creer)
        btnCreer.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }

    private  fun doConnect(): Boolean {

       var tmp = false
        if(validate())
        {
        val apiInterface = ApiInterface.create()

        apiInterface.seConnecter(
            txtLayoutUsername.text.toString(),
            txtLayoutPassword.text.toString()
        ).enqueue(object :
            Callback<User> {


            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Connexion error!", Toast.LENGTH_SHORT).show()

            }



            override fun onResponse(call: Call<User>, response: Response<User>) {



                val user = response.body()
                if (user != null) {
                    Toast.makeText(this@LoginActivity, "Login Success", Toast.LENGTH_SHORT).show()


                    startActivity(intent)
                } else {
                    Toast.makeText(this@LoginActivity, "User not found", Toast.LENGTH_SHORT).show()
                }
            }

        })

    }
        return tmp
        }


    private fun validate(): Boolean {
        txtLayoutUsername.error = null
        txtLayoutPassword.error = null


        if (txtLayoutUsername.text!!.isEmpty()){
            txtUsername.error = getString(R.string.mustNotBeEmpty)
            return false
        }

        if (txtLayoutPassword.text!!.isEmpty()){
            txtPassword.error = getString(R.string.mustNotBeEmpty)
            return false
        }


        return true
    }
}