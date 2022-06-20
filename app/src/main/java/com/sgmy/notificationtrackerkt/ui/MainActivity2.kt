package com.sgmy.notificationtrackerkt.ui

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.sgmy.notificationtrackerkt.R
import com.sgmy.notificationtrackerkt.databinding.ActivityMain2Binding
import com.sgmy.notificationtrackerkt.ui.fragment.AppListFragment

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


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
                    Log.i("XX","  NAV DASHBORD")

                }
                R.id.navigation_dashboard -> {
                    Log.i("XX","YY NAV DASHBORD")

                }

            }
            true
        }

          val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    Log.i("XX","YY NAV DASHBORD")

                }
                R.id.navigation_dashboard -> {

                    Log.i("XX","NAV DASHBORD")


                }
            }
            true
        }
     //   bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frlayout, AppListFragment())
            .addToBackStack("applistfragment")
            .commit()

    }


}