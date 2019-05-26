package com.droidmonk.appinfo.apps

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.droidmonk.appinfo.databinding.AppItemBinding
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.View


class AppListAdapter(private var apps: List<PackageInfo>,
                             private val mainViewModel: AppListViewModel
) : RecyclerView.Adapter<AppListAdapter.AppsViewHolder>(){
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

            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
            {
                binding.min.text= "Min SDK:"+item.applicationInfo.minSdkVersion.toString()
            }
            else
                binding.min.visibility=View.GONE

            val isDebuggable = 0 != item.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE
            binding.isDebugBuild=isDebuggable

            binding.listener=object : AppListActionListener {
                override fun onClickLogo() {
                    itemView.context.apply {
                        startActivity(packageManager.getLaunchIntentForPackage(item.packageName))
                    }
                }

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
                    mainViewModel.openAppEvent.value=item.packageName
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