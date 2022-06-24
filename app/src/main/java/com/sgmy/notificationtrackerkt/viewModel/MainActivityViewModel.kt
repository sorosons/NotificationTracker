package com.sgmy.notificationtrackerkt.viewModel

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.awesomedialog.*
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.sgmy.notificationtrackerkt.helpers.NotificationListener

import com.thecode.aestheticdialogs.*
import kotlin.system.exitProcess

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private var mInterstitialAd: InterstitialAd? = null


    private val context = getApplication<Application>().applicationContext
    private val mTAG: String = "MainActivityViewModel"

    var isHavePermission = MutableLiveData<Boolean>()


    val onNativeAdsIsLoad = MutableLiveData<Boolean>()
    val onIsPermission = MutableLiveData<Boolean>()
    fun loadInterstitialAd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(context,
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                    onNativeAdsIsLoad.postValue(false)
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {

                    mInterstitialAd = interstitialAd
                    onNativeAdsIsLoad.postValue(true)

                }

            })

        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d(mTAG, "Ad was clicked.")
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                Log.d(mTAG, "Ad dismissed fullscreen content.")
                mInterstitialAd = null
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                // Called when ad fails to show.
                Log.e(mTAG, "Ad failed to show fullscreen content.")
                mInterstitialAd = null
            }

            override fun onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d(mTAG, "Ad recorded an impression.")
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(mTAG, "Ad showed fullscreen content.")
            }
        }


    }

    fun showAd(activity: Activity) {
        mInterstitialAd?.show(activity)
    }

    fun showDialogAwesome(activity: Activity) {
        AwesomeDialog.build(activity)
            .title("İzin gerekli")
            .body("Uygulamanın çalışması için izin gereklasadaşdşa")
            .icon(com.thecode.aestheticdialogs.R.drawable.ic_cancel)
            .onPositive("İzin Ver") {
                openSettings(activity)
            }
            .onNegative("Vazgeç") {
                exitProcess(-1)
            }
    }

    fun showDialogAesthetic(activity: Activity) {
        AestheticDialog.Builder(activity, DialogStyle.FLAT, DialogType.SUCCESS)
            .setTitle("Title")
            .setMessage("Message")
            .setCancelable(false)
            .setDarkMode(true)
            .setGravity(Gravity.CENTER)
            .setAnimation(DialogAnimation.SHRINK)
            .setOnClickListener(object : OnDialogClickListener {
                override fun onClick(dialog: AestheticDialog.Builder) {
                    dialog.dismiss()
                }
            })
            .show()
    }

    private fun openSettings(activity: Activity) {
        val permissionsIntent =
            Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
        activity.startActivity(permissionsIntent)
    }
    fun haveNotificationPermission(): Boolean {
        val enabledListeners = Settings.Secure.getString(
            context.contentResolver,
            "enabled_notification_listeners"
        ).split(":")

        val stackerService = ComponentName(context, NotificationListener::class.java).flattenToString()
        return enabledListeners.contains(stackerService)
    }


}