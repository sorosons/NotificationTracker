package com.sgmy.notificationtrackerkt.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sgmy.notificationtrackerkt.R
import com.sgmy.notificationtrackerkt.adapters.MyCustomAdapter
import com.sgmy.notificationtrackerkt.adapters.MyNotificationAdapter
import com.sgmy.notificationtrackerkt.viewModel.AppListViewModel
import com.sgmy.notificationtrackerkt.viewModel.NotificationViewModel

class NotificationFragment : Fragment() {

    companion object {
        fun newInstance() = NotificationFragment()
    }

    lateinit var adapterim: MyNotificationAdapter
    private lateinit var viewModel: NotificationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.notification_fragment, container, false)
        viewModel = ViewModelProvider(this).get(NotificationViewModel::class.java)
        viewModel.getAllNot(context)

        viewModel.audioRecordsLiveData.observe(viewLifecycleOwner, Observer {
            val recyclerView = view.findViewById<RecyclerView>(R.id.list_recycler_view)
            recyclerView.layoutManager = LinearLayoutManager(context)
            adapterim = MyNotificationAdapter(it)
            recyclerView?.adapter = adapterim

        })

        return view
    }



}