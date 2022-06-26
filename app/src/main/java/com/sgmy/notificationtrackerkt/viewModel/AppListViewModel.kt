package com.sgmy.notificationtrackerkt.viewModel

import android.app.Activity
import android.app.Application
import android.content.ComponentName
import android.content.Context


import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.ApplicationInfo
import android.provider.Settings
import android.util.Log

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.awesomedialog.*

import com.sgmy.notificationtrackerkt.helpers.DBHelper
import com.sgmy.notificationtrackerkt.helpers.NotificationListener
import com.sgmy.notificationtrackerkt.model.AppListDataModel



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