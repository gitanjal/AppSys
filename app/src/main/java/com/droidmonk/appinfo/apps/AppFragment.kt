package com.droidmonk.appinfo.apps


import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.droidmonk.appinfo.R
import kotlinx.android.synthetic.main.fragment_apps.*

class AppFragment : Fragment() {

    private var items: ArrayList<PackageInfo> =  ArrayList<PackageInfo>()
    private var filterKey="all"

    private lateinit var listAdapter: AppListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_apps, container, false)
        setHasOptionsMenu(true)
        return view

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupListAdapter()
        updateList()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.app_fr_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?)=
        when(item?.itemId){
            R.id.menu_filter->
            {
                showFilteringPopUpMenu()
                true
            }
            else->false
        }


    private fun setupListAdapter() {

        listAdapter = AppListAdapter(items)
        app_list.layoutManager = LinearLayoutManager(activity)
        app_list.adapter = listAdapter

    }

    private fun showFilteringPopUpMenu() {
            PopupMenu(activity as AppCompatActivity, activity!!.findViewById<View>(R.id.menu_filter)).run {
                menuInflater.inflate(R.menu.filter_menu, menu)

                setOnMenuItemClickListener {
                    val filterKeyNew =
                        when (it.itemId) {
                            R.id.all -> "all"
                            R.id.system -> "system"
                            R.id.downloaded -> "downloaded"
                            else -> "all"
                        }
                    Toast.makeText(activity,filterKeyNew,Toast.LENGTH_LONG).show()

                    if(filterKeyNew!=filterKey)
                    {
                        filterKey=filterKeyNew
                    }

                    updateList()
                    true
                }
                show()
            }
    }

    fun updateList()
    {

            items= ArrayList<PackageInfo>()
            getList(this.filterKey)
            listAdapter.setAppList(items)

    }

    fun getList(filter: String) {

        when(filter)
        {
            "all"->items = activity?.packageManager?.getInstalledPackages(0) as ArrayList<PackageInfo>
            "system"->{
                for (pi in activity?.packageManager?.getInstalledPackages(0)!!)
                {
                    if (pi.applicationInfo.flags and (ApplicationInfo.FLAG_UPDATED_SYSTEM_APP or ApplicationInfo.FLAG_SYSTEM) > 0) {
                        items.add(pi)
                    }
                }
            }
            "downloaded"->{
                for (pi in activity?.packageManager?.getInstalledPackages(0)!!)
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
