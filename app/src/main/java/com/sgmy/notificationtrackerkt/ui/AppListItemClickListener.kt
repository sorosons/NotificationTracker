package com.sgmy.notificationtrackerkt.ui

import android.view.View
import com.sgmy.notificationtrackerkt.adapters.appListAdapter.ApplistAdapter
import com.sgmy.notificationtrackerkt.model.AppListDataModel

/**
 * Tüm uygulamalar listelendiğinde her item için listener oluşturuldu
 */
interface AppListItemClickListener {
    fun onAppsItemClickListener(view:
                                ApplistAdapter.ViewHolder, appListDataModel: AppListDataModel)
}