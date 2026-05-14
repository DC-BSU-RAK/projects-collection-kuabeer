package com.example.moodtunes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)

        // Link the adapter to the ViewPager2
        viewPager.adapter = ViewPagerAdapter(this)

        // The modern way to connect Tabs and ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.setIcon(R.drawable.icon_info)
                }
                1 -> {
                    tab.setIcon(R.drawable.icon_home)
                }
                2 -> {
                    tab.setIcon(R.drawable.icon_profile)
                }
            }
        }.attach()

    }
}