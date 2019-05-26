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
        val view:View= inflater.inflate(R.layout.fragment_main, container, false)
      /*  pager = activity?.findViewById(R.id.pager)!!
        tabs = activity?.findViewById(R.id.tabs)!!
*/
        return view;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prepareFragments()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setUpViewPager()
    }

    /*Prepare fragments*/
    fun prepareFragments()
    {
        fragmentList.add(AppFragment.newInstance(KEY_FILTER_DOWNLOADED))
        fragmentList.add(AppFragment.newInstance(KEY_FILTER_SYS))
        fragmentList.add(AppFragment.newInstance(KEY_FILTER_DEBUG))

        fragmentTitleList.add("Downloaded")
        fragmentTitleList.add("System")
        fragmentTitleList.add("Debug")

        adapter = PagerAdapter(this!!.childFragmentManager!!, fragmentList, fragmentTitleList)

    }

    /*Loda the viewpager*/
    fun setUpViewPager() {

          pager = activity?.findViewById(R.id.pager)!!
          tabs = activity?.findViewById(R.id.tabs)!!

         adapter = PagerAdapter(this!!.childFragmentManager!!, fragmentList, fragmentTitleList)
         pager.adapter = adapter
         tabs.setupWithViewPager(pager)

    }
}
