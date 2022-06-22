package com.sgmy.notificationtrackerkt.adapters.notificationAdapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sgmy.notificationtrackerkt.R
import com.sgmy.notificationtrackerkt.model.NotiDataModel

class NotificationViewHolder(viewGroup: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(viewGroup.context)
        .inflate(R.layout.row_notification_info, viewGroup, false)
) {

    private val appName by lazy { itemView.findViewById<TextView>(R.id.appName) }
    private val image by lazy { itemView.findViewById<ImageView>(R.id.appIcon) }

    fun bindTo(
        notificationDataModel: NotiDataModel,
        onItemClick: (view: View, notificationDataModel: NotiDataModel) -> Unit
    ) {

        appName.text = notificationDataModel.text

        with(itemView.context) {
            itemView.setOnClickListener {
                onItemClick(it, notificationDataModel)
            }
            val iconic: Drawable? = runCatching {
                // Get a default icon
                ContextCompat.getDrawable(this, android.R.drawable.ic_dialog_info)
            }.getOrElse {
                this.packageManager.getApplicationIcon(notificationDataModel.packageName.toString())
            }

            Glide.with(this)
                .load(iconic)
                .into(image)

        }
    }

}