package com.example.constun.view

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.constun.R
import com.example.constun.model.User
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.lolretrofit.utils.ApiInterface

class MainActivity : AppCompatActivity() {

    lateinit var btnsignUp : Button
    lateinit var btnsignIn : Button
    lateinit var SignUpLayout : View
    lateinit var SignInLayout : View
    lateinit var btnsignup : TextView
    lateinit var btnsignin : TextView

    lateinit var emailText : TextInputEditText
    lateinit var emailLayout : TextInputLayout
    lateinit var passwordText : TextInputEditText
    lateinit var passwordLayout : TextInputLayout
    lateinit var password2Text : TextInputEditText
    lateinit var password2Layout : TextInputLayout

    lateinit var email : TextInputEditText
    lateinit var password : TextInputEditText

    lateinit var emailsLayout : TextInputLayout
    lateinit var passwordsLayout : TextInputLayout



    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("MissingInflatedId", "UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailText =findViewById(R.id.eMail)
        emailLayout = findViewById(R.id.eMailLayout)

        passwordText = findViewById(R.id.password)
        passwordLayout = findViewById(R.id.passwordLayout)

        password2Text = findViewById(R.id.password2)
        password2Layout= findViewById(R.id.password2Layout)

        btnsignup = findViewById(R.id.signup)
        btnsignin = findViewById(R.id.signin)
        btnsignIn = findViewById(R.id.signIn)

        email = findViewById(R.id.eMails)
        password = findViewById(R.id.passwords)

        emailsLayout = findViewById(R.id.eMailsLayout)
        passwordsLayout = findViewById(R.id.passwordsLayout)


        btnsignin.setOnClickListener{

            btnsignin.background = resources.getDrawable(R.drawable.switch_trcks,null)
            btnsignin.setTextColor(resources.getColor(R.color.textColor,null))
            btnsignup.background=null
            SignInLayout.visibility = View.VISIBLE
            SignUpLayout.visibility = View.GONE
            btnsignup.setTextColor(resources.getColor(R.color.green_700,null))
        }

        btnsignup.setOnClickListener{

            btnsignup.background = resources.getDrawable(R.drawable.switch_trcks,null)
            btnsignup.setTextColor(resources.getColor(R.color.textColor,null))
            btnsignin.background=null
            SignUpLayout.visibility = View.VISIBLE
            SignInLayout.visibility = View.GONE
            btnsignin.setTextColor(resources.getColor(R.color.green_700,null))
        }
        SignUpLayout = findViewById(R.id.SignUpLayout)
        SignInLayout = findViewById(R.id.SignInLayout)
        btnsignUp = findViewById(R.id.signUp)


        btnsignUp.setOnClickListener{

            doLogin()

        }
        btnsignIn.setOnClickListener {

            doConnect()
        }

    }

    private fun doLogin() {

        if(validate_signup())
        {

        val apiInterface = ApiInterface.create()

        apiInterface.inscrir(
            emailText.text.toString(),
            passwordText.text.toString(),
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

    private  fun doConnect() {


        if(validate_signin())
        {
            val apiInterface = ApiInterface.create()

            apiInterface.seConnecter(email.text.toString(), password.text.toString()).enqueue(object : Callback<User> {


                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Connexion error!", Toast.LENGTH_SHORT).show()

                }



                override fun onResponse(call: Call<User>, response: Response<User>) {



                    val user = response.body()
                    if (user != null) {
                        Toast.makeText(this@MainActivity, "Login Success", Toast.LENGTH_SHORT).show()


                        startActivity(intent)
                    } else {
                        Toast.makeText(this@MainActivity, "User not found", Toast.LENGTH_SHORT).show()
                    }
                }

            })

        }
    }

    private fun validate_signup(): Boolean {
        emailLayout.error = null
        passwordLayout.error = null
        password2Layout.error = null

        if (emailText.text!!.isEmpty()){
            emailLayout.error = getString(R.string.mustNotBeEmpty)
            return false
        }

        if (passwordText.text!!.isEmpty()){
            passwordLayout.error = getString(R.string.mustNotBeEmpty)
            return false
        }
        return true
    }

    private fun validate_signin(): Boolean {
        emailsLayout.error = null
        passwordsLayout.error = null


        if (email.text!!.isEmpty()){
            emailsLayout.error = getString(R.string.mustNotBeEmpty)
            return false
        }

        if (password.text!!.isEmpty()){
            passwordsLayout.error = getString(R.string.mustNotBeEmpty)
            return false
        }
        return true
    }
}