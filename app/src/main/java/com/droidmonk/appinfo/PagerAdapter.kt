package com.droidmonk.appinfo

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.droidmonk.appinfo.apps.AppFragment

class PagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    val appFragments:ArrayList<Fragment> = ArrayList()
    val appFragmentTitles:ArrayList<String> = ArrayList()

    fun addFragment(fragment:Fragment,title:String)
    {
        appFragments.add(fragment)
        appFragmentTitles.add(title)
    }

    override fun getItem(position: Int): Fragment = appFragments.get(position)
    override fun getPageTitle(position: Int): String?=appFragmentTitles.get(position)
    override fun getCount(): Int = appFragments.size
}