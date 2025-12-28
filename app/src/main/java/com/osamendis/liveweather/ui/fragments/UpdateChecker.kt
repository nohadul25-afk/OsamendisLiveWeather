package com.osamendis.liveweather.ui.fragments
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import kotlinx.coroutines.*
import org.json.JSONObject
import java.net.URL

object UpdateChecker {
    fun checkUpdate(context: Context){
        GlobalScope.launch(Dispatchers.IO){
            try {
                val apiUrl="https://api.github.com/repos/nohadul25-afk/OsamendisLiveWeather/releases/latest"
                val json=JSONObject(URL(apiUrl).readText())
                val latest=json.getString("tag_name").replace("v","")
                val apkUrl=json.getJSONArray("assets").getJSONObject(0).getString("browser_download_url")
                val current=context.packageManager.getPackageInfo(context.packageName,0).versionName
                if(current!=latest){
                    withContext(Dispatchers.Main){
                        AlertDialog.Builder(context)
                            .setTitle("Update Available")
                            .setMessage("Version  is available. Update now?")
                            .setPositiveButton("Update"){_,_->context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(apkUrl)))}
                            .setNegativeButton("Later", null)
                            .show()
                    }
                }
            }catch(e:Exception){e.printStackTrace()}
        }
    }
}
