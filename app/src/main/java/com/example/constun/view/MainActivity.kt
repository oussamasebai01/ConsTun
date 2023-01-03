package com.example.constun.view

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.doAfterTextChanged
import com.example.constun.R
import com.example.constun.model.User
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.lolretrofit.utils.ApiInterface

const val PREF_NAME = "LOGIN_PREF_LOL"
const val LOGIN = "LOGIN"
const val ID = "ID"
const val PASSWORD = "PASSWORD"
const val IS_REMEMBRED = "IS_REMEMBRED"

class MainActivity : AppCompatActivity() {

    lateinit var btnsignUp : Button
    lateinit var btnsignIn : Button
    lateinit var SignUpLayout : View
    lateinit var SignInLayout : View
    lateinit var btnsignup : TextView
    lateinit var btnsignin : TextView
    lateinit var forgetPassword : TextView

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
    lateinit var cbRememberMe: CheckBox
    lateinit var cbRememberMe2: CheckBox

    lateinit var mSharedPref: SharedPreferences



    @SuppressLint("MissingInflatedId", "UseCompatLoadingForDrawables", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

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

        cbRememberMe = findViewById(R.id.cbRememberMe)
        cbRememberMe2 = findViewById(R.id.cbRememberMe2)
        forgetPassword = findViewById(R.id.forgetPassword)

        mSharedPref= getSharedPreferences("LOGIN_PREF_LOL",
            AppCompatActivity.MODE_PRIVATE)

         // mSharedPref.getString(LOGIN,"")
        if (mSharedPref.getBoolean(IS_REMEMBRED, false)){
                navigate()

        }
        forgetPassword.setOnClickListener{
            val intent = Intent (this,ForgetPassword::class.java)
            startActivity(intent)
        }


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
            btnsignUp.isEnabled=false
        val apiInterface = ApiInterface.create()
            val user = User(email =emailText.text.toString(), password =  passwordText.text.toString() )
        apiInterface.inscrir(user
        ).enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Connexion error!", Toast.LENGTH_SHORT).show()
                btnsignUp.isEnabled=true
            }
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(call: Call<User>, response: Response<User>) {

                val user = response.body()
                if (user!=null) {
                    Toast.makeText(this@MainActivity, "Registration Success", Toast.LENGTH_SHORT).show()
                    btnsignUp.isEnabled=true
                        mSharedPref.edit().apply{
                            putBoolean(IS_REMEMBRED, true)
                            putString(ID ,user._id)
                            putString(LOGIN, user.email)
                            putString(PASSWORD, user.password)
                        }.apply()

                        navigate()
                }

                   else{
                    Toast.makeText(this@MainActivity, "User Already Exist. Please Login", Toast.LENGTH_SHORT).show()
                    btnsignUp.isEnabled=true
                }
            }

        })
    }
    }

    private  fun doConnect() {


        if(validate_signin())
        {
            val apiInterface = ApiInterface.create()
            btnsignIn.isEnabled=false

            apiInterface.seConnecter(email.text.toString(), password.text.toString()).enqueue(object : Callback<User> {


                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Connexion error!", Toast.LENGTH_SHORT).show()
                    btnsignIn.isEnabled=true
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    val user = response.body()
                    if (user != null) {
                        Toast.makeText(this@MainActivity, "Login Success", Toast.LENGTH_SHORT).show()
                            mSharedPref.edit().apply{
                                putBoolean(IS_REMEMBRED, true)
                                putString(ID ,user._id)
                                putString(LOGIN, user.email)
                                putString(PASSWORD, user.password)
                            }.apply()
                            navigate()
                        btnsignIn.isEnabled=true
                    } else {
                        Toast.makeText(this@MainActivity, "User not found", Toast.LENGTH_SHORT).show()
                        btnsignIn.isEnabled=true
                    }
                }

            })

        }
    }

    private fun validate_signup(): Boolean {
        emailLayout.error = null
        passwordLayout.error = null
        password2Layout.error = null

        if ( !android.util.Patterns.EMAIL_ADDRESS.matcher(emailText.text.toString()).matches()) {
            emailLayout.error = getString(R.string.mustNotBeEmpty)
            return false
        }

        if (passwordText.text!!.isEmpty() || password2Text.text!!.isEmpty() || passwordText.text.toString() != password2Text.text.toString()||
            passwordText.text!!.toString().length<8 ){
            passwordLayout.error = getString(R.string.mustNotBeEmpty)
            return false
        }
        return true
    }

    private fun validate_signin(): Boolean {
        emailsLayout.error = null
        passwordsLayout.error = null

        if ( !android.util.Patterns.EMAIL_ADDRESS.matcher(emailText.text.toString()).matches()) {
            emailsLayout.error = getString(R.string.mustNotBeEmpty)
            return false
        }

        if (password.text!!.isEmpty()||passwordText.text!!.toString().length<8 ){
            passwordsLayout.error = getString(R.string.mustNotBeEmpty)
            return false
        }
        return true
    }
    private fun navigate(){
        val intent = Intent(this,HomeActivity::class.java)
        startActivity(intent)
    }
}