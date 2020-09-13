package com.example.newswires

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager

import com.example.newswires.Adapter.ViewHolder.ListSourceAdapter
import com.example.newswires.Interface.NewsService
import com.example.newswires.Model.WebSite
import com.example.newswires.common.Common
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dmax.dialog.SpotsDialog
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {

    lateinit var layoutManager: LinearLayoutManager
    lateinit var mService: NewsService
    lateinit var adapter: ListSourceAdapter
    lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Paper.init(this)

        mService = Common.newsService

        swipe_to_refresh.setOnRefreshListener {
            loadWebSiteSource(true)
        }

        recycler_view_source_news.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recycler_view_source_news.layoutManager = layoutManager

        dialog = SpotsDialog(this)

        loadWebSiteSource(false)

    }

    private fun loadWebSiteSource(isRefresh: Boolean) {

        if (!isRefresh) {
            val cache = Paper.book().read<String>("cache")
            if (cache != null && !cache.isBlank()) {
                val webSite = Gson().fromJson<WebSite>(cache, WebSite::class.java)
                adapter = ListSourceAdapter(baseContext, webSite)
                adapter.notifyDataSetChanged()
                recycler_view_source_news.adapter = adapter
            } else {
                dialog.show()
                mService.sources.enqueue(object : retrofit2.Callback<WebSite> {
                    override fun onFailure(call: Call<WebSite>, t: Throwable) {
                        Toast.makeText(baseContext, "Failed", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<WebSite>, response: Response<WebSite>) {
                        adapter = ListSourceAdapter(baseContext, response.body()!!)
                        adapter.notifyDataSetChanged()
                        recycler_view_source_news.adapter = adapter

                        Paper.book().write("cache", Gson().toJson(response!!.body()!!))

                        dialog.dismiss()
                    }

                })
            }
        }
        else{
            swipe_to_refresh.isRefreshing=true
            mService.sources.enqueue(object : retrofit2.Callback<WebSite> {
                override fun onFailure(call: Call<WebSite>, t: Throwable) {
                    Toast.makeText(baseContext, "Failed", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<WebSite>, response: Response<WebSite>) {
                    adapter = ListSourceAdapter(baseContext, response.body()!!)
                    adapter.notifyDataSetChanged()
                    recycler_view_source_news.adapter = adapter

                    Paper.book().write("cache", Gson().toJson(response!!.body()!!))

                    swipe_to_refresh.isRefreshing=false
                }

            })


        }

    }
}
