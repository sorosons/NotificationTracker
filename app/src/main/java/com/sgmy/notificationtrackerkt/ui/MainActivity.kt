package com.sgmy.notificationtrackerkt.ui

import android.Manifest

import android.content.ComponentName
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.MobileAds
import com.sgmy.notificationtrackerkt.R
import com.sgmy.notificationtrackerkt.databinding.ActivityMain2Binding
import com.sgmy.notificationtrackerkt.helpers.NotificationListener
import com.sgmy.notificationtrackerkt.ui.fragment.AppListFragment
import com.sgmy.notificationtrackerkt.ui.fragment.NotificationFragment

import com.sgmy.notificationtrackerkt.viewModel.MainActivityViewModel



class MainActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {

    private lateinit var binding: ActivityMain2Binding

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
       // startNotificationListener()
        if(!viewModel.haveNotificationPermission()){
        viewModel.showDialogAwesome(this)
        }

    }

    private fun handleBottomNavigation(
        menuItemId: Int
    ): Boolean = when (menuItemId) {
        R.id.navigation_home -> {
            if (interstitialIsLoaded)
                viewModel.showAd(activity = this)
            swapFragments(AppListFragment())
            true
        }
        R.id.navigation_dashboard -> {
            if (interstitialIsLoaded)
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




    override fun onRestart() {
        super.onRestart()
        if(!viewModel.haveNotificationPermission()){
            viewModel.showDialogAwesome(this)
        }
    }



}








