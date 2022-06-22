package com.sgmy.notificationtrackerkt.adapters.notificationAdapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sgmy.notificationtrackerkt.model.NotiDataModel

class NotificationAdapter(
    private val notList: List<NotiDataModel>,
    private val onItemClick: (view: View, notificationDataModel: NotiDataModel) -> Unit
) : RecyclerView.Adapter<NotificationViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        return NotificationViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return notList.size
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bindTo(notList[position])
        { view, notificationDataModel -> onItemClick(view, notificationDataModel) }

    }

}