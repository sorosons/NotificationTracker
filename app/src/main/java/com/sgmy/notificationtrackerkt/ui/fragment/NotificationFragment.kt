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
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.sgmy.notificationtrackerkt.R
import com.sgmy.notificationtrackerkt.adapters.notificationAdapter.NotificationAdapter
import com.sgmy.notificationtrackerkt.adapters.notificationAdapter.NotificationViewHolder
import com.sgmy.notificationtrackerkt.databinding.AppListFragmentBinding
import com.sgmy.notificationtrackerkt.databinding.NotificationFragmentBinding
import com.sgmy.notificationtrackerkt.model.NotiDataModel
import com.sgmy.notificationtrackerkt.viewModel.NotificationViewModel


class NotificationFragment : Fragment() {

    /**
     * View Binding entegrasyon
     */
    private var _binding: NotificationFragmentBinding? = null
    private val binding get() = _binding!!


    private val posts = ArrayList<NotiDataModel>()
    private val adapter = NotificationAdapter(posts) { view, notificationdatamodel ->
    //Toast.makeText(context, " " + notificationdatamodel.packageName, Toast.LENGTH_SHORT).show()
    }

    lateinit var recyclerView: RecyclerView


    lateinit var mAdView: AdView


    companion object {
        fun newInstance() = NotificationFragment()
    }

    lateinit var adapterim: NotificationAdapter
    private lateinit var viewModel: NotificationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = NotificationFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root


        MobileAds.initialize(requireContext()) {}

        //set Banner Ads
        setBannerAds()

        viewModel = ViewModelProvider(this).get(NotificationViewModel::class.java)
        viewModel.getAllNot(context)

        recyclerView = binding.listRecyclerView

        recyclerView.apply {
            this.layoutManager = LinearLayoutManager(requireContext())
        }
        getNotification()

        return root
    }


    private fun getNotification() {
        viewModel.audioRecordsLiveData.observe(viewLifecycleOwner, Observer { fetchList ->




            for (i in fetchList)
                posts.add(i)

             posts.reverse()
            recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()

        })
    }

    private fun setBannerAds() {
        mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }


}