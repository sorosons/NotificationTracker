package com.sgmy.notificationtrackerkt.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sgmy.notificationtrackerkt.R
import com.sgmy.notificationtrackerkt.adapters.MyCustomAdapter
import com.sgmy.notificationtrackerkt.viewModel.AppListViewModel

class AppListFragment : Fragment() {

    companion object {
        fun newInstance() = AppListFragment()
    }

    init {

    }
    private lateinit var viewModel: AppListViewModel

    lateinit var adapterim:MyCustomAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       var view=inflater.inflate(R.layout.app_list_fragment, container, false)





        viewModel = ViewModelProvider(this).get(AppListViewModel::class.java)
        viewModel.getApplist(context)
        viewModel.audioRecordsLiveData.observe(viewLifecycleOwner, Observer {
            val recyclerView = view.findViewById<RecyclerView>(R.id.list_recycler_view)
            recyclerView.layoutManager = LinearLayoutManager(context)
            adapterim = MyCustomAdapter(it)
            recyclerView?.adapter = adapterim
            Log.i("ALO:", it.get(0).myAppName.toString())
        })

        var switch = view.findViewById<Switch>(R.id.switchall)




        switch.setOnCheckedChangeListener { _, isChecked ->

            if(isChecked)
            {
                viewModel.selectAllApps()
            }
            else{
                viewModel.unSelectAllApps()
            }

            adapterim.allSwitch=isChecked
            adapterim.notifyDataSetChanged()

        }




        return view
    }





}
