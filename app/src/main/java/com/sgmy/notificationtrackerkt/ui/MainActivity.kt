package com.sgmy.notificationtrackerkt.ui

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.MobileAds
import com.sgmy.notificationtrackerkt.R
import com.sgmy.notificationtrackerkt.databinding.ActivityMain2Binding
import com.sgmy.notificationtrackerkt.ui.fragment.AppListFragment
import com.sgmy.notificationtrackerkt.ui.fragment.NotificationFragment
import com.sgmy.notificationtrackerkt.viewModel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding

    var navView: BottomNavigationView? =null

    private lateinit var viewModel: MainActivityViewModel

    private var interstitialIsLoaded: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        MobileAds.initialize(this) {}

        viewModel.loadInterstitialAd()

        viewModel.onNativeAdsIsLoad.observe(this, Observer {
            interstitialIsLoaded = it
        })

        binding.navView.setOnItemSelectedListener {
            handleBottomNavigation(
                it.itemId
            )
        }
        binding.navView.selectedItemId = R.id.navigation_home
    }

    private fun handleBottomNavigation(
        menuItemId: Int
    ): Boolean = when(menuItemId) {
        R.id.navigation_home -> {
            if(interstitialIsLoaded)
                viewModel.showAd(activity = this)
            swapFragments(AppListFragment())
            true
        }
        R.id.navigation_dashboard -> {
            if(interstitialIsLoaded)
                viewModel.showAd(activity = this)
            swapFragments(NotificationFragment())
            true
        }
        else -> false
    }

    private fun swapFragments(fragment: Fragment) {

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }



}