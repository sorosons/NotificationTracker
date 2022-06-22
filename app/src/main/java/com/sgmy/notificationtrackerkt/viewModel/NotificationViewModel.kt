package com.sgmy.notificationtrackerkt.viewModel

import android.R
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notification_ap.DBHelper
import com.sgmy.notificationtrackerkt.model.NotiDataModel

class NotificationViewModel : ViewModel() {

    lateinit var db: DBHelper
    var audioRecordsLiveData: MutableLiveData<ArrayList<NotiDataModel>> = MutableLiveData()
    var notiarray: ArrayList<NotiDataModel>? = ArrayList<NotiDataModel>()

    fun getAllNot(context: Context?) {

        db = DBHelper(context!!, null)
        notiarray = db.getName()
        audioRecordsLiveData.postValue(notiarray);


    }

  fun getNotificationIcon(){


    }

}