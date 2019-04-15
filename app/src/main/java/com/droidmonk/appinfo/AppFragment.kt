package com.droidmonk.appinfo


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.droidmonk.appinfo.databinding.FragmentAppsBinding
import java.util.ArrayList


class AppFragment : Fragment() {

    private lateinit var viewDataBinding:FragmentAppsBinding
    private lateinit var listAdapter: AppListAdapter

    private var filterKey:String=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewDataBinding= FragmentAppsBinding.inflate(inflater,container,false).apply {
            viewmodel= obtainViewModel(MainViewModel::class.java)
        }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListAdapter()
    }

    override fun onStart() {
        super.onStart()

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
