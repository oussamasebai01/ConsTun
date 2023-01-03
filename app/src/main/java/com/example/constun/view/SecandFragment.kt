package com.example.constun.view

import MATRICUL
import android.content.Intent
import android.content.Intent.getIntent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.example.constun.R
const val NAME = "CIENTB"
const val NUMEROB = "22222222"
const val MATRICULEB ="1TUN1"
const val CODEB = "1"
const val CINB = "01"
const val NOMB = "constun"
class SecandFragment : Fragment() {

    lateinit var next : TextView
    lateinit var fm : FragmentManager
    lateinit var ft : FragmentTransaction
    lateinit var mSharedPref: SharedPreferences
    lateinit var nomB : EditText
    lateinit var matriculB :EditText
    lateinit var codeB : EditText
    lateinit var cinB : EditText
    lateinit var numB : EditText
    lateinit var btnScan : Button
    lateinit var editQR1 : EditText
    lateinit var mSharedPrefs: SharedPreferences

    private fun verif():Boolean{
        if (nomB.text!!.isEmpty()||matriculB.text!!.isEmpty()||codeB.text!!.isEmpty()||cinB.text!!.isEmpty()||numB.text!!.isEmpty())
            return false
        else
            return true
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_secand, container, false)
        next = view.findViewById(R.id.next2)
        mSharedPref= view.context.getSharedPreferences("LOGIN_PREF_LOL",
            AppCompatActivity.MODE_PRIVATE
        )
        mSharedPrefs = view.context.getSharedPreferences(NAME, AppCompatActivity.MODE_PRIVATE)
        nomB = view.findViewById(R.id.nomB)
        matriculB= view.findViewById(R.id.matriculeB)
        codeB = view.findViewById(R.id.codeB)
        cinB = view.findViewById(R.id.cinB)
        numB = view.findViewById(R.id.NumTelB)
        btnScan = view.findViewById(R.id.btnScan)
        editQR1 = view.findViewById(R.id.editQR1)
        btnScan.setOnClickListener {
            val scanActivityIntent = Intent(getActivity(), scanActivity::class.java)
            startActivity(scanActivityIntent)
        }
        editQR1.setText(requireActivity().getIntent().getStringExtra("data"))
        val separated: List<String> = editQR1.text.split(":")
        matriculB.setText(separated.getOrNull(0))
        codeB.setText(separated.getOrNull(1))
        cinB.setText(separated.getOrNull(2))
        numB.setText(separated.getOrNull(3))
        next.setOnClickListener {
            if (verif()) {
                mSharedPrefs.edit().apply {
                    putString(NOMB, nomB.text.toString())
                    putString(NUMEROB, numB.text.toString())
                    putString(MATRICULEB, matriculB.text.toString())
                    putString(CODEB, codeB.text.toString())
                    putString(CINB, cinB.text.toString())
                }.apply()

                fm = requireFragmentManager()
                ft = fm.beginTransaction()
                ft.replace(R.id.fragmentContainerView2, ThirdFragment())
                ft.commit()

            }
            else
                Toast.makeText(requireContext(),"empty field", Toast.LENGTH_SHORT).show()
        }
        println( "11111111"+mSharedPref.getString(NOMA,""))
        println("222222"+ mSharedPrefs.getString(NOMB,""))
        println("33333333"+mSharedPref.getString(MATRICUL,""))
        println("44444444"+mSharedPref.getString(NOMB,""))
        return view
    }
}