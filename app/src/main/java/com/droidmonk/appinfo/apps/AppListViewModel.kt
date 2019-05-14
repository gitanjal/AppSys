package com.droidmonk.appinfo.apps

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import com.droidmonk.appinfo.SingleLiveEvent

class AppListViewModel(val app: Application) : ViewModel() {

    var items: ArrayList<PackageInfo> = ArrayList()//= emptyList<PackageInfo>() as ArrayList<PackageInfo>
    var itemsDownloaded: ArrayList<PackageInfo> = ArrayList() //= emptyList<PackageInfo>() as ArrayList<PackageInfo>
    var itemsSystem: ArrayList<PackageInfo> = ArrayList()//= emptyList<PackageInfo>() as ArrayList<PackageInfo>
    var itemsDebug: ArrayList<PackageInfo> = ArrayList() //= emptyList<PackageInfo>() as ArrayList<PackageInfo>

    internal val openAppEvent = SingleLiveEvent<String>()


    fun start(filterKey: String)
    {
        when(filterKey){
            AppFragment.KEY_FILTER_DOWNLOADED->{
                if(itemsDownloaded.size==0)
                    filterApps(filterKey)
                items=itemsDownloaded
            }
            AppFragment.KEY_FILTER_SYS->{
                if(itemsSystem.size==0)
                    filterApps(filterKey)

                items=itemsSystem
            }
            AppFragment.KEY_FILTER_DEBUG->
            {
                if(itemsDebug.size==0)
                    filterApps(AppFragment.KEY_FILTER_DEBUG)
                items=itemsDebug
            }
            else->
            {
                items= app.packageManager.getInstalledPackages(0) as ArrayList<PackageInfo>
            }
        }

    }

    fun filterApps(filterKey: String)
    {
        for (pi in app.packageManager.getInstalledPackages(0))
        {
            val isDebuggable = 0 != pi.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE
            if(isDebuggable)
                itemsDebug.add(pi)

            if (pi.applicationInfo.flags and (ApplicationInfo.FLAG_UPDATED_SYSTEM_APP or ApplicationInfo.FLAG_SYSTEM) > 0) {
                itemsSystem.add(pi)
            } else {
                itemsDownloaded.add(pi)
            }
        }
    }

}