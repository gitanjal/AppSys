package com.droidmonk.appinfo

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.droidmonk.appinfo.appdetails.AppDetailsViewModel
import com.droidmonk.appinfo.apps.AppListViewModel

class ViewModelFactory private constructor (private val app:Application): ViewModelProvider.NewInstanceFactory() {



    override fun <T : ViewModel?> create(modelClass: Class<T>)=
        with(modelClass)
        {
            when{
                isAssignableFrom(AppListViewModel::class.java)-> AppListViewModel(
                    app
                )
                isAssignableFrom(AppDetailsViewModel::class.java)->AppDetailsViewModel(app)
                else -> {
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            }
        } as T

    companion object {
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application) =
            INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                INSTANCE ?: ViewModelFactory(application)
                    .also { INSTANCE = it }
            }
    }
}