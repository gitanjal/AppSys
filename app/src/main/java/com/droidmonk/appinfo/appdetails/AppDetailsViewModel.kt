package com.droidmonk.appinfo.appdetails

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import java.text.SimpleDateFormat
import java.util.*

class AppDetailsViewModel(application:Application): AndroidViewModel(application) {

     var packageInfo = MutableLiveData<PackageInfo>()
     var appInfoLive = MutableLiveData<ApplicationInfo>()
     lateinit var appInfo:ApplicationInfo
     var appName = MutableLiveData<String>()
     var appInstallDate = MutableLiveData<String>()
     var appUpdateDate = MutableLiveData<String>()
     var appIcon = MutableLiveData<Drawable>()
     var build = MutableLiveData<Boolean>()
     var minSDK = MutableLiveData<String>()

    fun start(packageName:String)
    {
        packageInfo.value=getApplication<Application>().packageManager.getPackageInfo(packageName,0)
        appInfo=getApplication<Application>().packageManager.getApplicationInfo(packageName,0)
        appInfoLive.value=appInfo

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.N)
            minSDK.value=appInfo.minSdkVersion.toString()

        appInstallDate.value = SimpleDateFormat("MM/dd/yyyy").format(Date(packageInfo!!.value!!.firstInstallTime))
        appUpdateDate.value = SimpleDateFormat("MM/dd/yyyy").format(Date(packageInfo!!.value!!.lastUpdateTime))

        appName.value=appInfo.loadLabel(getApplication<Application>().packageManager).toString()
        appIcon.value=appInfo.loadIcon(getApplication<Application>().packageManager)

        build.value = 0 !=appInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE
    }


}