package com.droidmonk.appinfo

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.droidmonk.appinfo.databinding.AppItemBinding
import android.support.v4.content.ContextCompat.startActivity
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.os.Environment.getExternalStoragePublicDirectory
import android.support.v4.content.ContextCompat.startActivity
import android.content.pm.PackageManager
import android.provider.Settings
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.View
import java.io.*
import android.support.v4.content.ContextCompat.startActivity
import android.support.v4.content.ContextCompat.startActivity
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import com.google.common.reflect.Reflection.getPackageName


class AppListAdapter(private var apps: List<PackageInfo>,
                             private val mainViewModel: MainViewModel) : RecyclerView.Adapter<AppListAdapter.AppsViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AppsViewHolder {
        val inflater= LayoutInflater.from(p0.context)
        val binding=AppItemBinding.inflate(inflater)
        return AppsViewHolder(binding)
    }

    override fun getItemCount(): Int = apps.size
    override fun onBindViewHolder(holder: AppsViewHolder, position: Int) {
        holder.bind(apps.get(position))


    }

    inner class AppsViewHolder (val binding: AppItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item:PackageInfo)
        {
            binding.appName=item.applicationInfo.loadLabel(itemView.context.packageManager).toString()
            binding.appIcon=item.applicationInfo.loadIcon(itemView.context.packageManager)
            binding.appInfo=item.applicationInfo
            binding.packageInfo=item

            val isDebuggable = 0 != item.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE
            binding.isDebugBuild=isDebuggable

            binding.listener=object : AppListActionListener{
                override fun openAppSettings() {
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    val uri = Uri.fromParts("package", item.packageName, null)
                    intent.data = uri
                    itemView.context.startActivity(intent)
                }

                override fun shareAPK() {
                   shareApplication(item,itemView.context)
                }

                override fun onClick() {

                }

                override fun goToPlayStore() {

                    try {
                        var intent:Intent=Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${item.applicationInfo.packageName}"))
                        itemView.context.startActivity(intent)
                    } catch (anfe: android.content.ActivityNotFoundException) {
                        itemView.context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=${item.applicationInfo.packageName}")
                            )
                        )
                    }


                }
            }
            binding.executePendingBindings()
        }
    }

    fun replaceData(apps: List<PackageInfo>) {
        setList(apps)
    }

    private fun setList(apps: List<PackageInfo>) {
        this.apps = apps
        notifyDataSetChanged()
    }

    private fun shareApplication(item:PackageInfo,context: Context) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=${item.applicationInfo.packageName}" )
            putExtra(Intent.EXTRA_SUBJECT,"Share this app" )
            type = "text/plain"
        }
        context.startActivity(sendIntent)
    }



}