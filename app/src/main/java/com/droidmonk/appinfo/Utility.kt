package com.droidmonk.appinfo

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.net.Uri

import java.io.*

class Utility {
    private fun shareApplication(item:PackageInfo,context: Context) {
        val app = item.applicationInfo
        val filePath = app.sourceDir

        val intent = Intent(Intent.ACTION_SEND)

        // MIME of .apk is "application/vnd.android.package-archive".
        // but Bluetooth does not accept this. Let's use "*/*" instead.
        intent.type = "*/*"

        // Append file and send Intent
        val originalApk = File(filePath)

        try {
            //Make new directory in new location
            var tempFile = File(context.externalCacheDir!!.toString() + "/ExtractedApk")
            //If directory doesn't exists create new
            if (!tempFile.isDirectory)
                if (!tempFile.mkdirs())
                    return
            //Get application's name and convert to lowercase
            tempFile =
                    File(tempFile.path + "/" + context.getString(app.labelRes).replace(" ", "").toLowerCase() + ".apk")
            //If file doesn't exists create new
            if (!tempFile.exists()) {
                if (!tempFile.createNewFile()) {
                    return
                }
            }
            //Copy file to new location
            val `in` = FileInputStream(originalApk)
            val out = FileOutputStream(tempFile)

            val buf = ByteArray(1024)
            var len: Int
            len = `in`.read(buf)
            while (len > 0) {
                out.write(buf, 0, len)
                len = `in`.read(buf)
            }
            `in`.close()
            out.close()
            println("File copied.")
            //Open share dialog
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(tempFile))
            context.startActivity(Intent.createChooser(intent, "Share app via"))

        } catch (e: IOException) {
            e.printStackTrace()
        }

    }


}
