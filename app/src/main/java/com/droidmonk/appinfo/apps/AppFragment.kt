package com.droidmonk.appinfo.apps


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.droidmonk.appinfo.R
import com.droidmonk.appinfo.databinding.FragmentAppsBinding
import com.droidmonk.appinfo.obtainViewModel
import java.util.ArrayList


class AppFragment : Fragment() {

    private lateinit var viewDataBinding:FragmentAppsBinding
    private lateinit var listAdapter: AppListAdapter
    private lateinit var navController: NavController

    private var filterKey:String=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewDataBinding= FragmentAppsBinding.inflate(inflater,container,false).apply {
            viewmodel =
                obtainViewModel(AppListViewModel::class.java).apply{
                    openAppEvent.observe(this@AppFragment, Observer<String> { packageName ->
                    if (packageName != null) {
                        openAppDetails(packageName)
                    }
                })
        }
        }
        return viewDataBinding.root
    }

    private fun openAppDetails(packageName: String) {

        var bundle = Bundle().apply {
            putString("packageName",packageName)
        }
        navController.navigate(R.id.appDetailsFragment,bundle)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListAdapter()
        val host: NavHostFragment = activity?.supportFragmentManager
            ?.findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return

        navController = host.navController

    }


    override fun onResume() {
        super.onResume()
        filterKey=arguments?.getString(KEY_FILTER)?:""
        viewDataBinding.viewmodel?.start(filterKey)
    }

    private fun setupListAdapter() {

        viewDataBinding.appList.layoutManager= LinearLayoutManager(activity)
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            listAdapter = AppListAdapter(ArrayList(0), viewModel)
            viewDataBinding.appList.adapter = listAdapter
        } else {
         //   Log.w(TAG, "ViewModel not initialized when attempting to set up adapter.")
        }
    }

    companion object {
        val KEY_FILTER="key_filter"
        val KEY_FILTER_DOWNLOADED:String="downloaded"
        val KEY_FILTER_SYS:String="system"
        val KEY_FILTER_DEBUG:String="debug"
        fun newInstance(filterKey: String) = AppFragment().apply {
            arguments=Bundle().apply {
                putString(KEY_FILTER,filterKey)
            }
        }
    }
}
