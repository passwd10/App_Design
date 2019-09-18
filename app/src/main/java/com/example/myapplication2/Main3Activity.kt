package com.example.myapplication2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import kotlinx.android.synthetic.main.activity_main.view.*

class Main3Activity : AppCompatActivity() {

    lateinit var menu1Fragment: Menu1Fragment
    lateinit var menu2Fragment: Menu2Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationItemView : BottomNavigationItemView = findViewById(R.id.bottom_navigation_view)


        bottomNavigationItemView.bottom_navigation_view.setOnNavigationItemReselectedListener {
            when(it.itemId) {
                R.id.navigation_menu1 -> {
                    menu1Fragment = Menu1Fragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, menu1Fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

                R.id.navigation_menu2 -> {
                    menu2Fragment = Menu2Fragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, menu2Fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
            }

        }
    }
}
