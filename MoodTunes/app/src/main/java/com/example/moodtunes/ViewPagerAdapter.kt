package com.example.moodtunes

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    // Set the total number of screens to be displayed
    override fun getItemCount(): Int = 3


    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> InstructionFragment()
            1 -> HomeFragment()
            else -> ProfileFragment()
        }
    }
}