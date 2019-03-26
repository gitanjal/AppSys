package com.droidmonk.appinfo

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.hardware.Sensor
import android.hardware.Sensor.TYPE_ALL
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.util.Log


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewFragment()
      //  getAppList()
      //  getSensorList()
//        getDisplayInfo()
    }

    private fun setupViewFragment() {
        supportFragmentManager.findFragmentById(R.id.contentFrame) ?:
        MainFragment.newInstance().let {
            replaceFragmentInActivity(it, R.id.contentFrame)
        }
    }

    private fun getDisplayInfo() {

        /*resolution*/
        val display = windowManager.defaultDisplay
        var displaymetrics = DisplayMetrics()
        display.getMetrics(displaymetrics)
        displaymetrics.heightPixels
        displaymetrics.widthPixels
        Log.d("tag","Resolution:${displaymetrics.heightPixels}*${displaymetrics.widthPixels}")
        Log.d("tag","Density:${displaymetrics.density}")
        Log.d("tag","Density DPI:${displaymetrics.densityDpi}")
        Log.d("tag","Scaled Density :${displaymetrics.scaledDensity}")


    }

    private fun getSensorList() {
        var sensorManger:SensorManager=getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensorList:List<Sensor> = sensorManger.getSensorList(TYPE_ALL)

        for (sensor in sensorList)
        {
            Log.d("tag",sensor.name.toString())

        }
    }

    private fun getAppList() {
        var appList:List<PackageInfo> = packageManager.getInstalledPackages(0)

        for(app in appList)
        {
            Log.d("tag",app.applicationInfo.loadLabel(getPackageManager()).toString())
            Log.d("tag",app.applicationInfo.packageName.toString())
            Log.d("tag",app.applicationInfo.targetSdkVersion.toString())
            Log.d("tag",app.applicationInfo.minSdkVersion.toString())
            Log.d("tag",app.applicationInfo.publicSourceDir.toString())
            Log.d("tag",app.applicationInfo.loadIcon(packageManager).toString())

            if (app.applicationInfo.flags and (ApplicationInfo.FLAG_UPDATED_SYSTEM_APP or ApplicationInfo.FLAG_SYSTEM) > 0) {
                // It is a system app
                Log.d("tag","SYSTEM------")

            } else {
                // It is installed by the user
                Log.d("tag","NON SYSTEM----------")
            }

        }
    }
}
