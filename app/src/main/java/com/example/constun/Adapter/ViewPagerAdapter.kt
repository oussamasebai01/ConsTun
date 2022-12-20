package com.example.constun.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter( list: ArrayList<Fragment>,fm:FragmentManager , lifecycle : Lifecycle) :FragmentStateAdapter(fm,lifecycle){

   private val fragmentListe =list
    override fun getItemCount(): Int {
        return fragmentListe.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentListe[position]
    }

}