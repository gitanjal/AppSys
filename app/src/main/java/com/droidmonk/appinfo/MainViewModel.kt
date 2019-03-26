package com.droidmonk.appinfo

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.pm.PackageInfo
import android.databinding.ObservableArrayList
import android.databinding.ObservableList

class MainViewModel(val app: Application) : ViewModel() {

    var items: List<PackageInfo> = emptyList()

    fun start()
    {
        items= app.packageManager.getInstalledPackages(0)
    }
}