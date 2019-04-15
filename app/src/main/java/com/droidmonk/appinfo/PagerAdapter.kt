package com.droidmonk.appinfo

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class PagerAdapter(fragmentManager: FragmentManager,val appFragments:List<AppFragment>,val appFragmentTitles:List<String>) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment = appFragments.get(position)
    override fun getPageTitle(position: Int): String?=appFragmentTitles.get(position)
    override fun getCount(): Int = appFragments.size
}