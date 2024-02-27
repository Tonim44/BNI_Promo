package id.co.tonim.bniapppromo

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Process
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.co.tonim.bniapppromo.adapter.PromoAdapter
import id.co.tonim.bniapppromo.data.PromoItem
import id.co.tonim.bniapppromo.databinding.ActivityMainBinding
import id.co.tonim.bniapppromo.tools.LoadingDialog
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var promoList: MutableList<PromoItem>
    private lateinit var adapter: PromoAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var loading: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        promoList = mutableListOf()
        adapter = PromoAdapter(promoList)
        recyclerView.adapter = adapter

        loading = LoadingDialog(this)
        loading.startLoading()

        fetchPromos()
    }

    private fun fetchPromos() {
        val url = "https://content.digi46.id/promos"

        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                try {
                    val jsonArray = JSONArray(responseData)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val name = jsonObject.getString("nama")
                        val date = jsonObject.getString("createdAt")
                        val desc = jsonObject.getString("desc")
                        val imageUrl = jsonObject.getJSONObject("img")
                            .getJSONObject("formats")
                            .getJSONObject("medium")
                            .getString("url")

                        val promoItem = PromoItem(name, date, desc, imageUrl)
                        promoList.add(promoItem)
                    }

                    runOnUiThread {
                        adapter.notifyDataSetChanged()
                        loading.isDismiss()
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                    loading.isDismiss()
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                loading.isDismiss()
            }
        })
    }

    override fun onBackPressed() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Keluar")
        alertDialogBuilder
            .setMessage("Apakah Anda Yakin Untuk Menutup Aplikasi ?")
            .setCancelable(false)
            .setPositiveButton(
                "Iya"
            ) { dialog, id ->
                moveTaskToBack(true)
                Process.killProcess(Process.myPid())
                System.exit(1)
            }
            .setNegativeButton(
                "Tidak"
            ) { dialog, id -> dialog.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

}
