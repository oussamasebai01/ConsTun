package com.example.constun.view

import ProfileFragment
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager

import androidx.fragment.app.Fragment
import com.example.constun.R
import com.example.constun.databinding.ActivityHomeBinding
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlinx.android.synthetic.main.activity_home.*


@Suppress("UNUSED_EXPRESSION")
class HomeActivity : AppCompatActivity() {


    lateinit var binding:ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        remplacefragment(HomeFragment())


        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home ->{remplacefragment(HomeFragment())
                    it.isEnabled=false
                    bottomNavigationView.menu.findItem(R.id.profile).isEnabled = true
                    bottomNavigationView.menu.findItem(R.id.settings).isEnabled = true}
                R.id.profile ->{remplacefragment(ProfileFragment())
                    it.isEnabled = false
                    bottomNavigationView.menu.findItem(R.id.home).isEnabled = true
                    bottomNavigationView.menu.findItem(R.id.settings).isEnabled = true}
                R.id.settings ->{remplacefragment(SettingsFragment())
                    it.isEnabled=false
                    bottomNavigationView.menu.findItem(R.id.profile).isEnabled=true
                    bottomNavigationView.menu.findItem(R.id.home).isEnabled = true}
                else -> {
                }
            }
            true

        }

    }

    private fun remplacefragment(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction =  fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}