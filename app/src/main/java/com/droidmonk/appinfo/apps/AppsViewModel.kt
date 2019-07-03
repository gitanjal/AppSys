package com.droidmonk.appinfo.apps

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import androidx.lifecycle.AndroidViewModel
import android.R
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Handler
import androidx.core.os.HandlerCompat.postDelayed



/*ViewModel without LiveData*/
class AppsViewModel(application:Application): AndroidViewModel(application) {

    /*
    * We won't need this interface when we use LiveData
    * */
    interface AppsEventListener
    {
        fun onListChange(items:ArrayList<PackageInfo>)
    }
    private lateinit var lisener:AppsEventListener

    private val context: Context = application.applicationContext
    private var items: ArrayList<PackageInfo> =  ArrayList<PackageInfo>()
    var filterKey="all"
        set(value) {

            field = value
            updateList()

        }

    fun start()
    {
        updateList()
    }

    fun setListener(listener:AppsEventListener)
    {
        this.lisener=listener
    }


    fun updateList()
    {

            items.clear()

            GetAppsAsyncTask(lisener,filterKey,context.packageManager).execute()


    }


    /*fun getList(filter: String) {

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

        this.lisener.onListChange(items)

    }
*/

    class GetAppsAsyncTask(listener:AppsEventListener,filter: String,pm:PackageManager) : AsyncTask<Void, Void, ArrayList<PackageInfo>>() {
        var listener:AppsEventListener
        var filterStr:String
        var packageManager:PackageManager
//        var applicationContext:Context
        private var items: ArrayList<PackageInfo> =  ArrayList<PackageInfo>()

        init {
            this.listener=listener
            this.filterStr=filter
            this.packageManager=pm
//            this.applicationContext=context
        }

        override fun doInBackground(vararg params: Void?): ArrayList<PackageInfo> {
            Thread.sleep(60000)
            when(filterStr)
            {
                "all"->items = packageManager?.getInstalledPackages(0) as ArrayList<PackageInfo>
                "system"->{
                    for (pi in packageManager?.getInstalledPackages(0)!!)
                    {
                        if (pi.applicationInfo.flags and (ApplicationInfo.FLAG_UPDATED_SYSTEM_APP or ApplicationInfo.FLAG_SYSTEM) > 0) {
                            items.add(pi)
                        }
                    }
                }
                "downloaded"->{
                    for (pi in packageManager?.getInstalledPackages(0)!!)
                    {
                        if (pi.applicationInfo.flags and (ApplicationInfo.FLAG_UPDATED_SYSTEM_APP or ApplicationInfo.FLAG_SYSTEM) > 0) {

                        } else {
                            items.add(pi)
                        }
                    }
                }
            }

            return items
        }

        override fun onPostExecute(result: ArrayList<PackageInfo>?) {
            super.onPostExecute(result)

            result?.let { this.listener.onListChange(it) }

        }

    }


}