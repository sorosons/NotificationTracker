package com.sgmy.notificationtrackerkt.adapters

import android.R.attr.bitmap
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.sgmy.notificationtrackerkt.R
import com.sgmy.notificationtrackerkt.model.NotiDataModel
import java.io.ByteArrayOutputStream


class MyNotificationAdapter(private val mList: List<NotiDataModel>) :
    RecyclerView.Adapter<MyNotificationAdapter.ViewHolder>() {




    // create new views



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_notification_info, parent, false)


        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        val stream=ByteArrayOutputStream()






        holder.imageView.setImageDrawable(ItemsViewModel.bigIcon)

        // sets the text to the textview from our itemHolder class
        holder.textView.text = ItemsViewModel.text


    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.appIcon)
        val textView: TextView = itemView.findViewById(R.id.appName)

    }

}