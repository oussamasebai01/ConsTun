package com.example.constun.view

import CODE
import MATRICUL
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.example.constun.R
import com.example.constun.view.HomeFragment.Companion.newInstance
import com.google.zxing.client.result.EmailDoCoMoResultParser

const val NOMA = "constun"
const val NUMERO = "22222222"

const val CIN = "01"

class FirstFragment : Fragment() {
        lateinit var next : TextView
        lateinit var fm :FragmentManager
        lateinit var ft : FragmentTransaction
        lateinit var nomA : EditText
        lateinit var matriculA :EditText
        lateinit var codeA : EditText
        lateinit var cinA : EditText
        lateinit var numA : EditText
        lateinit var mSharedPref: SharedPreferences

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,

    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        mSharedPref= view.context.getSharedPreferences("LOGIN_PREF_LOL",
            AppCompatActivity.MODE_PRIVATE
        )
        nomA = view.findViewById(R.id.nomA)
        matriculA = view.findViewById(R.id.matriculeAZ)
        codeA = view.findViewById(R.id.codeA)
        cinA = view.findViewById(R.id.cinA)
        numA = view.findViewById(R.id.NumTelA)
       next= view.findViewById(R.id.next1)
        matriculA.setText(mSharedPref.getString(MATRICUL,""))
        codeA.setText(mSharedPref.getString(CODE,""))
        cinA.setText(mSharedPref.getString(CIN,""))
        numA.setText(mSharedPref.getString(NUMERO,""))
        next.setOnClickListener {
            fm= requireFragmentManager()
             ft = fm.beginTransaction()
            ft.replace(R.id.fragmentContainerView2, SecandFragment())
            ft.commit()
            mSharedPref.edit().apply{
                putString(NOMA, nomA.text.toString())

            }.apply()
        }
        return view
    }


}