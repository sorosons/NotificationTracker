package com.sgmy.notificationtrackerkt.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.notification_ap.DBHelper
import com.sgmy.notificationtrackerkt.R
import com.sgmy.notificationtrackerkt.model.AppListDataModel


class MyCustomAdapter(private val mList: List<AppListDataModel>) :
    RecyclerView.Adapter<MyCustomAdapter.ViewHolder>() {


    var allSwitch: Boolean = false
    lateinit var db : DBHelper

    // create new views



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_applist_info, parent, false)

        db=DBHelper(parent.context,null)
        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        holder.imageView.setImageDrawable(ItemsViewModel.appIcon)

        // sets the text to the textview from our itemHolder class
        holder.textView.text = ItemsViewModel.myAppName


        holder.switchim.isChecked = allSwitch

        holder.switchim.isChecked=db.isExist(ItemsViewModel.packageName.toString())



        holder.switchim.setOnClickListener(){

            if( !holder.switchim.isChecked)
                db.deleteCourse(ItemsViewModel.packageName!!)
            else
                db.addAppsName(ItemsViewModel)
        }


    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.appIcon)
        val textView: TextView = itemView.findViewById(R.id.appName)
        val switchim: Switch = itemView.findViewById(R.id.switchRow)
    }





}