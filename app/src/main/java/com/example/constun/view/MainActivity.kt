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

    lateinit var txtUsername: TextInputEditText
    lateinit var txtLayoutUsername: TextInputLayout

    lateinit var txtPassword: TextInputEditText
    lateinit var txtLayoutPassword: TextInputLayout

    lateinit var txtNum: TextInputEditText
    lateinit var txtLayoutNum: TextInputLayout

    lateinit var cbRememberMe: CheckBox
    lateinit var btnLogin: Button
    lateinit var btnconnexion : Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtUsername =findViewById(R.id.txtUsername)
        txtLayoutUsername = findViewById(R.id.txtLayoutUsername)

        txtPassword = findViewById(R.id.txtPassword)
        txtLayoutPassword = findViewById(R.id.txtLayoutPassword)

        txtNum = findViewById(R.id.txtNum)
        txtLayoutNum = findViewById(R.id.txtLayoutNum)

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
            txtUsername.text.toString(),
            txtPassword.text.toString(),
            txtNum.text.toString().toInt()
        ).enqueue(object : Callback<User> {


            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Connexion error!", Toast.LENGTH_SHORT).show()

            }


            override fun onResponse(call: Call<User>, response: Response<User>) {

                val user = response.body()
                val name = response.body()?.username

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
        txtLayoutUsername.error = null
        txtLayoutPassword.error = null
        txtLayoutNum.error = null

        if (txtUsername.text!!.isEmpty()){
            txtLayoutUsername.error = getString(R.string.mustNotBeEmpty)
            return false
        }

        if (txtPassword.text!!.isEmpty()){
            txtLayoutPassword.error = getString(R.string.mustNotBeEmpty)
            return false
        }

        if (txtNum.text!!.isEmpty()){
            txtLayoutNum.error = getString(R.string.mustNotBeEmpty)
            return false
        }

        return true
    }
}