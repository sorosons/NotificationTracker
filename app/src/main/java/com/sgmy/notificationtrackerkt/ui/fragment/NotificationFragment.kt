package com.sgmy.notificationtrackerkt.ui.fragment


import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sgmy.notificationtrackerkt.R
import com.sgmy.notificationtrackerkt.adapters.notificationAdapter.NotificationAdapter
import com.sgmy.notificationtrackerkt.adapters.notificationAdapter.NotificationViewHolder
import com.sgmy.notificationtrackerkt.model.NotiDataModel
import com.sgmy.notificationtrackerkt.viewModel.NotificationViewModel


class NotificationFragment : Fragment()  {

    private val posts = ArrayList<NotiDataModel>()
    private val adapter = NotificationAdapter(posts) { view, notificationdatamodel ->
       // Toast.makeText(context, " " + notificationdatamodel.packageName, Toast.LENGTH_SHORT).show()
    }

    lateinit var recyclerView: RecyclerView

    companion object {
        fun newInstance() = NotificationFragment()
    }

    lateinit var adapterim: NotificationAdapter
    private lateinit var viewModel: NotificationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(com.sgmy.notificationtrackerkt.R.layout.notification_fragment, container, false)
        viewModel = ViewModelProvider(this).get(NotificationViewModel::class.java)
        viewModel.getAllNot(context)

        recyclerView = view.findViewById<RecyclerView>(R.id.list_recycler_view)

        recyclerView.apply {
            this.layoutManager = LinearLayoutManager(requireContext())
        }
        getNotification()

        return view
    }


    private fun getNotification() {
        viewModel.audioRecordsLiveData.observe(viewLifecycleOwner, Observer { fetchList ->

            for (i in fetchList)
                posts.add(i)

            recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()


        })


    }


}