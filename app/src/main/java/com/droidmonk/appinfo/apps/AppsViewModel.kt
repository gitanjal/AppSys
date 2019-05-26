package com.droidmonk.appinfo.apps

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import androidx.lifecycle.AndroidViewModel

/*ViewModel without LiveData*/
class AppsViewModel(application:Application): AndroidViewModel(application) {

    /*
    * We won't need this interface when we use LiveData
    * */
    interface AppListEventListener
    {
        fun onListChange(items:ArrayList<PackageInfo>)
    }
    private lateinit var lisener:AppListEventListener

    private val context: Context = application.applicationContext
    private var items: ArrayList<PackageInfo> =  ArrayList<PackageInfo>()
    private var filterKey=""



    fun start()
    {
        updateList()
    }

    fun setListener(listener:AppListEventListener)
    {
        this.lisener=listener
    }


    fun updateList(filterKey:String="all")
    {
        if(filterKey!=this.filterKey)
        {
            getList(filterKey)
        }
    }


    fun getList(filter: String) {

        when(filter)
        {
            "all"->items = context.packageManager?.getInstalledPackages(0) as ArrayList<PackageInfo>
            "system"->{
                for (pi in context.packageManager?.getInstalledPackages(0)!!)
                {
                    if (pi.applicationInfo.flags and (ApplicationInfo.FLAG_UPDATED_SYSTEM_APP or ApplicationInfo.FLAG_SYSTEM) > 0) {
                        items.add(pi)
                    }
                }
            }
            "downloaded"->{
                for (pi in context.packageManager?.getInstalledPackages(0)!!)
                {
                    if (pi.applicationInfo.flags and (ApplicationInfo.FLAG_UPDATED_SYSTEM_APP or ApplicationInfo.FLAG_SYSTEM) > 0) {

                    } else {
                        items.add(pi)
                    }
                }
            }
        }

    }

}