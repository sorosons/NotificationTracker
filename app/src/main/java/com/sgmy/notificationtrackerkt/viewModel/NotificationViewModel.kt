package com.sgmy.notificationtrackerkt.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sgmy.notificationtrackerkt.helpers.DBHelper
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



}