package com.sgmy.notificationtrackerkt.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.sgmy.notificationtrackerkt.adapters.appListAdapter.ApplistAdapter
import com.sgmy.notificationtrackerkt.databinding.AppListFragmentBinding
import com.sgmy.notificationtrackerkt.helpers.DBHelper
import com.sgmy.notificationtrackerkt.model.AppListDataModel
import com.sgmy.notificationtrackerkt.ui.AppListItemClickListener
import com.sgmy.notificationtrackerkt.ui.MainActivity
import com.sgmy.notificationtrackerkt.viewModel.AppListViewModel
import com.sgmy.notificationtrackerkt.viewModel.MainActivityViewModel


class AppListFragment : Fragment(), AppListItemClickListener {

    companion object {
        fun newInstance() = AppListFragment()
    }

    /**
     * View Binding entegrasyon
     */
    private var _binding: AppListFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AppListViewModel

    private lateinit var mainActivityViewModel: MainActivityViewModel

    private lateinit var adapterim: ApplistAdapter

    lateinit var db: DBHelper

    private lateinit var mAdView: AdView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AppListFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root


        db = DBHelper(requireContext(), null)

        viewModel = ViewModelProvider(this).get(AppListViewModel::class.java)
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        viewModel.getApplist(context)



        viewModel.audioRecordsLiveData.observe(viewLifecycleOwner, Observer {
            val recyclerView = binding.listRecyclerView
            recyclerView.layoutManager = LinearLayoutManager(context)
            adapterim = ApplistAdapter(requireContext(), it, this)
            recyclerView?.adapter = adapterim
        })

        MobileAds.initialize(requireContext()) {}

        //set Banner Ads
        setBannerAds()



        binding.switchall.setOnCheckedChangeListener { _, isChecked ->


            if (mainActivityViewModel.haveNotificationPermission()) {
                if (isChecked) {
                    viewModel.selectAllApps()
                } else {
                    viewModel.unSelectAllApps()
                }

                adapterim.allSwitch = isChecked
                adapterim.notifyDataSetChanged()
            } else {
                mainActivityViewModel.showDialogAwesome(requireActivity())
                binding.switchall.isChecked = false

            }


        }



        return root
    }


    private fun setBannerAds() {
        mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }


    override fun onAppsItemClickListener(
        view: ApplistAdapter.ViewHolder,
        appListDataModel: AppListDataModel
    ) {


        view.switchim.setOnClickListener {
            if (mainActivityViewModel.haveNotificationPermission()) {
                if (!view.switchim.isChecked)
                    db.deleteCourse(appListDataModel.packageName!!)
                else
                    db.addAppsName(appListDataModel)
            } else {
                mainActivityViewModel.showDialogAwesome(requireActivity())
                view.switchim.isChecked = false
            }

        }
    }


}
