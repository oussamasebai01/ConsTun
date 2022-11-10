package com.example.constun.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import com.example.constun.R
import com.example.constun.model.User
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.lolretrofit.utils.ApiInterface

class MainActivity : AppCompatActivity() {

    lateinit var txtEmail: TextInputEditText
    lateinit var txtLayoutEmail: TextInputLayout

    lateinit var txtPassword: TextInputEditText
    lateinit var txtLayoutPassword: TextInputLayout

    lateinit var txtPassword2: TextInputEditText
    lateinit var txtLayoutPassword2: TextInputLayout

    lateinit var cbRememberMe: CheckBox
    lateinit var btnLogin: Button
    lateinit var btnconnexion : Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtEmail =findViewById(R.id.txtEmail)
        txtLayoutEmail = findViewById(R.id.txtLayoutEmail)

        txtPassword = findViewById(R.id.txtPassword)
        txtLayoutPassword = findViewById(R.id.txtLayoutPassword)

        txtPassword2 = findViewById(R.id.txtPassword2)
        txtLayoutPassword2 = findViewById(R.id.txtLayoutPassword2)

        cbRememberMe = findViewById(R.id.cbRememberMe)
        btnLogin = findViewById(R.id.btnLogin)
        btnconnexion = findViewById(R.id.connexion)

        btnLogin.setOnClickListener{

            doLogin()
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
        }


        btnconnexion.setOnClickListener{
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun doLogin() {

        if(validate())
        {

        val apiInterface = ApiInterface.create()

        apiInterface.inscrir(
            txtEmail.text.toString(),
            txtPassword.text.toString(),
        ).enqueue(object : Callback<User> {


            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Connexion error!", Toast.LENGTH_SHORT).show()

            }


            override fun onResponse(call: Call<User>, response: Response<User>) {

                val user = response.body()

                if (user!=null) {
                    Toast.makeText(this@MainActivity, "Registration Success", Toast.LENGTH_SHORT).show()
                }

                   else{
                    Toast.makeText(this@MainActivity, "User Already Exist. Please Login", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }
    }

    private fun validate(): Boolean {
        txtLayoutEmail.error = null
        txtLayoutPassword.error = null
        txtLayoutPassword2.error = null

        if (txtEmail.text!!.isEmpty()){
            txtLayoutEmail.error = getString(R.string.mustNotBeEmpty)
            return false
        }

        if (txtPassword.text!!.isEmpty()){
            txtLayoutPassword.error = getString(R.string.mustNotBeEmpty)
            return false
        }


        return true
    }
}