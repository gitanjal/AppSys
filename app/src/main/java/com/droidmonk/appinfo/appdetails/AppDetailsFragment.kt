package com.droidmonk.appinfo.appdetails


import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.droidmonk.appinfo.R
import com.droidmonk.appinfo.databinding.FragmentAppDetailsBinding
import com.droidmonk.appinfo.obtainViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */


class AppDetailsFragment : Fragment() {

    lateinit var appDetailsViewModel: AppDetailsViewModel
    lateinit var dataBinding: FragmentAppDetailsBinding

    override fun onResume() {
        super.onResume()

        arguments?.getString("packageName")?.let { dataBinding.viewModel?.start(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding= FragmentAppDetailsBinding.inflate(inflater,container,false).apply {
            viewModel=obtainViewModel(AppDetailsViewModel::class.java)
        }

        dataBinding.tvPlaystore.setOnClickListener {
            arguments?.getString("packageName")?.let { goToPlayStore(it) }

        }

        dataBinding.appLogo.setOnClickListener{
            arguments?.getString("packageName")?.let {
                startActivity(activity?.packageManager?.getLaunchIntentForPackage(it))
            }
        }

        dataBinding.tvSettings.setOnClickListener {

            arguments?.getString("packageName")?.let {

                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", it, null)
                intent.data = uri
                startActivity(intent)

            }
        }

        dataBinding.tvShare.setOnClickListener {

            arguments?.getString("packageName")?.let{
                shareApplication(it)
            }

        }

        dataBinding.apkmirror.setOnClickListener {

            arguments?.getString("packageName")?.let{

                val intent=Intent().apply {
                    action=Intent.ACTION_VIEW
                    data=Uri.parse("https://www.apkmirror.com/?post_type=app_release&searchtype=apk&s=${it}")
                }
                startActivity(intent)

            }




        }

        dataBinding.viewModel?.minSDK?.observe(this@AppDetailsFragment, Observer {
            dataBinding.min.text=it
        })


        return dataBinding.root
    }

    fun goToPlayStore(packageName: String) {

        try {
            var intent: Intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${packageName}"))
            startActivity(intent)
        } catch (anfe: android.content.ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=${packageName}")
                )
            )
        }


    }

    private fun shareApplication(packageName: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=${packageName}" )
            putExtra(Intent.EXTRA_SUBJECT,"Share this app" )
            type = "text/plain"
        }
        startActivity(sendIntent)
    }

}
