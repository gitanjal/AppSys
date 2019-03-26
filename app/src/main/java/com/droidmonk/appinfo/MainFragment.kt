package com.droidmonk.appinfo


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.droidmonk.appinfo.databinding.FragmentMainBinding
import java.util.ArrayList


class MainFragment : Fragment() {

    private lateinit var viewDataBinding:FragmentMainBinding
    private lateinit var listAdapter: AppListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewDataBinding= FragmentMainBinding.inflate(inflater,container,false).apply {
            viewmodel= (activity as MainActivity).obtainViewModel(MainViewModel::class.java)
        }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListAdapter()
    }

    override fun onStart() {
        super.onStart()
        viewDataBinding.viewmodel?.start()
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
        fun newInstance() = MainFragment()

    }
}
