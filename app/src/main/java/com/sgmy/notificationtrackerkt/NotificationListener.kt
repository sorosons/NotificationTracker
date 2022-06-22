package com.sgmy.notificationtrackerkt

import android.R
import android.app.Notification
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.notification_ap.DBHelper
import com.sgmy.notificationtrackerkt.model.NotiDataModel
import java.io.ByteArrayOutputStream


class NotificationListener: NotificationListenerService() {
    override fun onBind(intent: Intent): IBinder? {
        return super.onBind(intent)
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        // Implement what you want here

        val db = DBHelper(this, null)
        val pack = sbn.packageName
        var ticker = ""

        if(db.isExist(pack)){
            Log.i("Hey:pack Var",pack)
            if (sbn.notification.tickerText != null) {
                ticker = sbn.notification.tickerText.toString()
            }
            var extras: Bundle? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                extras = sbn.notification.extras
            }
            val title = extras!!.getString("android.title")
            val text = extras.getCharSequence("android.text").toString()
            val id1 = extras.getInt(Notification.EXTRA_SMALL_ICON)
            val icon = sbn.notification.largeIcon
            //Convert btimap to ByteArrray
            val iconX =
                this.packageManager.getApplicationIcon(pack)




            val stream = ByteArrayOutputStream()
            icon?.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray = stream.toByteArray()

            Log.i("Package", pack)
            Log.i("Ticker", ticker)
            if (title != null) {
                Log.i("Title",title)
            }
            Log.i("Text", text)

            val data = NotiDataModel(pack,title,text,iconX)
            db.addNotificaiton(data)
        }
        else{
            Log.i("packgage delete from db",pack)
        }



    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        // Implement what you want here
    }
}