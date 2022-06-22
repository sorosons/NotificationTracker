package com.sgmy.notificationtrackerkt.ui

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.sgmy.notificationtrackerkt.R
import com.sgmy.notificationtrackerkt.databinding.ActivityMain2Binding
import com.sgmy.notificationtrackerkt.ui.fragment.AppListFragment
import com.sgmy.notificationtrackerkt.ui.fragment.NotificationFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        supportFragmentManager.beginTransaction()
            .replace(R.id.frlayout, AppListFragment())
            .addToBackStack("applistfragment")
            .commit()


        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main2)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard
            )
        )





        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)



        navView.setOnItemSelectedListener() {
            when (it.itemId) {
                R.id.navigation_home -> {
                    loadFragment(AppListFragment())

                }
                R.id.navigation_dashboard -> {
                    loadFragment(NotificationFragment())

                }

            }
            true
        }








    }
  private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}