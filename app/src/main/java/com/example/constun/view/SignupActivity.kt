package com.example.constun.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintSet.Layout
import com.example.constun.R

class SignupActivity : AppCompatActivity() {

    lateinit var btnsignUp : Button
    lateinit var SignUpLayout : View
    lateinit var SignInLayout : View
    lateinit var btnsignup : TextView
    lateinit var btnsignin : TextView

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

         btnsignUp = findViewById(R.id.signUp)
         SignUpLayout = findViewById(R.id.SignUpLayout)
         SignInLayout = findViewById(R.id.SignInLayout)
         btnsignup = findViewById( R.id.signup)
         btnsignin = findViewById( R.id.signin)
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
        btnsignUp.setOnClickListener {
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
        }

    }
}