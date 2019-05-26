package com.droidmonk.appinfo.apps

import android.content.pm.PackageInfo
import android.os.Build
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.droidmonk.appinfo.R
import kotlinx.android.synthetic.main.app_item.view.*


class AppListAdapter(private var apps: List<PackageInfo>
) : RecyclerView.Adapter<AppListAdapter.AppsViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AppsViewHolder {
        val inflater= LayoutInflater.from(p0.context)
        val view=inflater.inflate(R.layout.app_item,p0,false)
        return AppsViewHolder(view)
    }

    fun setAppList(list:ArrayList<PackageInfo>)
    {
        apps=list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = apps.size
    override fun onBindViewHolder(holder: AppsViewHolder, position: Int) {
        holder.bind(apps.get(position))
    }

    inner class AppsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        fun bind(item:PackageInfo)
        {
            itemView.title.text=item.applicationInfo.loadLabel(itemView.context.packageManager).toString()
            itemView.package_name.text=item.applicationInfo.packageName
            itemView.version.text="Version : "+item.versionName+" ("+item.versionCode+")"
            itemView.app_icon.setImageDrawable(item.applicationInfo.loadIcon(itemView.context.packageManager))

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                itemView.min.text="Min SDK:"+item.applicationInfo.minSdkVersion.toString()
            }
            else
                itemView.min.visibility=View.GONE

        }
    }

}