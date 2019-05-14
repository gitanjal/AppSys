package com.droidmonk.appinfo


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.droidmonk.appinfo.apps.AppFragment
import com.droidmonk.appinfo.apps.AppFragment.Companion.KEY_FILTER_DEBUG
import com.droidmonk.appinfo.apps.AppFragment.Companion.KEY_FILTER_DOWNLOADED
import com.droidmonk.appinfo.apps.AppFragment.Companion.KEY_FILTER_SYS

/**
 * A simple [Fragment] subclass.
 *
 */
class MainFragment : Fragment() {

    companion object {
        fun newInstance()=MainFragment()
    }

    private lateinit var pager: ViewPager
    private lateinit var tabs:TabLayout
    private var fragmentList: ArrayList<AppFragment> =  ArrayList<AppFragment>()
    private var fragmentTitleList: ArrayList<String> =  ArrayList<String>()
    private lateinit var adapter:PagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onResume() {
        super.onResume()


        if(fragmentList.size==0) {
            fragmentList.add(AppFragment.newInstance(KEY_FILTER_DOWNLOADED))
            fragmentList.add(AppFragment.newInstance(KEY_FILTER_SYS))
            fragmentList.add(AppFragment.newInstance(KEY_FILTER_DEBUG))

            fragmentTitleList.add("Downloaded")
            fragmentTitleList.add("System")
            fragmentTitleList.add("Debug")
        }
        pager = activity?.findViewById(R.id.pager)!!
        tabs = activity?.findViewById(R.id.tabs)!!

        adapter = PagerAdapter(this!!.childFragmentManager!!, fragmentList, fragmentTitleList)
        pager.adapter = adapter

        tabs.setupWithViewPager(pager)

    }
}
