package com.sgmy.notificationtrackerkt.viewModel

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.ApplicationInfo
import android.view.Gravity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.awesomedialog.*
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.sgmy.notificationtrackerkt.R
import com.sgmy.notificationtrackerkt.helpers.DBHelper
import com.sgmy.notificationtrackerkt.model.AppListDataModel
import com.sgmy.notificationtrackerkt.ui.MainActivity
import com.thecode.aestheticdialogs.*
import kotlin.system.exitProcess


class AppListViewModel(
    application: Application
) : AndroidViewModel(application) {



    private val context = getApplication<Application>().applicationContext


    var apps: ArrayList<AppListDataModel>? =ArrayList<AppListDataModel>()

    var audioRecordsLiveData: MutableLiveData<ArrayList<AppListDataModel>> = MutableLiveData()
    val isHaveNotificationPermission= MutableLiveData<Boolean>()

    lateinit var db : DBHelper

    fun getApplist(context: Context?) {

        db = DBHelper(context!!, null)

        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
   //     val pkgAppsList: List<ResolveInfo>? =
     //       context?.getPackageManager()?.queryIntentActivities(mainIntent, 0)
        val packs: List<PackageInfo>? = context?.getPackageManager()?.getInstalledPackages(0)


        for (i in packs!!.indices) {
            val p = packs!![i]
            if (!isSystemPackage(p)) {
                val appName = p.applicationInfo.loadLabel( context?.getPackageManager()).toString()
                val icon = p.applicationInfo.loadIcon( context?.getPackageManager())
                val packages = p.applicationInfo.packageName


                apps?.add(AppListDataModel(packages, appName, icon))
            }
        }
          audioRecordsLiveData.postValue(apps);


    }
    private fun isSystemPackage(pkgInfo: PackageInfo): Boolean {
        return pkgInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
    }

    fun selectAllApps()
    {
        for (item in apps!!) {
            db.addAppsName(item)
        }

    }

    fun unSelectAllApps()
    {
        for (item in apps!!) {
            db.deleteCourse(item.packageName)
        }
    }












}