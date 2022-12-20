package com.example.constun.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.constun.R
import kotlinx.android.synthetic.main.activity_create1.*

class CreatActivity2 : AppCompatActivity() {
    lateinit var btnNext :Button
    lateinit var nomBText : EditText
    lateinit var btnScan : Button
    lateinit var matriculB : EditText
    lateinit var codeB :EditText
    lateinit var cinB : EditText
    lateinit var numB : EditText
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creat2)
        nomBText = findViewById(R.id.nomB)
        btnNext = findViewById(R.id.next)
        btnScan = findViewById(R.id.btnScan)
        matriculB = findViewById(R.id.matriculeB)
        codeB = findViewById(R.id.codeB)
        cinB = findViewById(R.id.cinB)
        numB = findViewById(R.id.cinB)

        val intent = intent
        var  nomA=intent.getStringExtra("nomA")
        var matriculA=intent.getStringExtra("matriculA")
        var codeA = intent.getStringExtra("codeA")
        var cinA = intent.getStringExtra("cin")
        var numA = intent.getStringExtra("num")
        println("hello"+numA)
        //val message = intent.getStringExtra(EXTRA_MESSAGE)
       // nomBText.setText(message)
        btnNext.setOnClickListener {
            val intent = Intent(this,CreatActivity3::class.java)
            intent.putExtra("nomA",nomA)
            intent.putExtra("matriculA",matriculA.toString())
            intent.putExtra("codeA",codeA)
            intent.putExtra("cinA",cinA)
            intent.putExtra("numA",numA.toString())
            intent.putExtra("nomB",nomBText.text.toString())
            intent.putExtra("matriculB",matriculB.text.toString())
            intent.putExtra("codeB",codeB.text.toString())
            intent.putExtra("cinB",cinB.text.toString())
            intent.putExtra("numB",numB.text.toString())
            startActivity(intent)
        }
        btnScan.setOnClickListener {
            val scanActivityIntent = Intent(this, scanActivity::class.java)
            startActivity(scanActivityIntent)



//            if (requireActivity().intent.hasExtra("data")) {
//                editQR1.setText(requireActivity().intent.getStringExtra("key_name"))
//            }
        }
    }
}