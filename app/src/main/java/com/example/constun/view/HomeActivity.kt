package com.example.constun.view

import ProfileFragment
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.fragment.app.Fragment
import com.example.constun.R
import com.example.constun.databinding.ActivityHomeBinding


@Suppress("UNUSED_EXPRESSION")
class HomeActivity : AppCompatActivity() {


    lateinit var binding:ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        remplacefragment(HomeFragment())

        binding.bottomNavigationView.setOnItemReselectedListener {
            when(it.itemId){

                R.id.home ->remplacefragment(HomeFragment())
                R.id.profile ->remplacefragment(ProfileFragment())
                R.id.settings ->remplacefragment(SettingsFragment())
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