package com.droidmonk.appinfo


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.droidmonk.appinfo.apps.AppFragment
import com.droidmonk.appinfo.apps.AppFragment.Companion.KEY_FILTER_DEBUG
import com.droidmonk.appinfo.apps.AppFragment.Companion.KEY_FILTER_DOWNLOADED
import com.droidmonk.appinfo.apps.AppFragment.Companion.KEY_FILTER_SYS
import com.droidmonk.appinfo.databinding.FragmentMainBinding

/**
 * A simple [Fragment] subclass.
 *
 */
class MainFragment : Fragment() {


    private lateinit var adapter:PagerAdapter
    private lateinit var dataBinding:FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding= FragmentMainBinding.inflate(inflater,container,false)  //inflater.inflate(R.layout.fragment_main, container, false)
        return dataBinding.root;
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpViewPager()
    }

    /*Prepare fragments*/
    fun prepareFragments()
    {
        adapter = PagerAdapter(this!!.childFragmentManager!!)
        adapter.addFragment(AppFragment.newInstance(KEY_FILTER_DOWNLOADED),"Downloaded")
        adapter.addFragment(AppFragment.newInstance(KEY_FILTER_SYS),"System")
        adapter.addFragment(AppFragment.newInstance(KEY_FILTER_DEBUG),"Debug")
    }

    /*Loda the viewpager*/
    fun setUpViewPager() {

        prepareFragments()

        dataBinding.pager.adapter = adapter
        dataBinding.tabs.setupWithViewPager(dataBinding.pager)

    }
}
