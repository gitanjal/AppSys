package com.droidmonk.appinfo.apps


import android.content.pm.PackageInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.PopupMenu
import android.view.*
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.droidmonk.appinfo.R
import kotlinx.android.synthetic.main.fragment_apps.*

class AppFragment : Fragment() {

    private lateinit var listAdapter: AppListAdapter
    private lateinit var viewModel: AppsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel= ViewModelProviders.of(this).get(AppsViewModel::class.java)
        viewModel.setListener(object : AppsViewModel.AppsEventListener{
            override fun onListChange(items: ArrayList<PackageInfo>) {

                listAdapter.setAppList(items)

            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_apps, container, false)
        setHasOptionsMenu(true)
        return view

    }

    override fun onResume() {
        super.onResume()

        viewModel.start()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        setupListAdapter()

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

        listAdapter = AppListAdapter(ArrayList())

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
                    viewModel.filterKey=filterKeyNew
                    true
                }
                show()
            }
    }




}
