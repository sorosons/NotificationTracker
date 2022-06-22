package com.sgmy.notificationtrackerkt.ui

import com.sgmy.notificationtrackerkt.model.AppListDataModel

/**
 * Tüm uygulamalar listelendiğinde her item için listener oluşturuldu
 */
interface AppListItemClickListener {
    fun onAppsItemClickListener(appListDataModel: AppListDataModel)
}