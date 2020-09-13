package com.example.newswires.Interface

import com.example.newswires.Model.News
import com.example.newswires.Model.WebSite
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface NewsService {

    @get:GET("v2/sources?apiKey=20bf7227cef04b9c81daff4c9c2e71e3")

    val sources: Call<WebSite>



    @GET
    fun getNewsFromSource(@Url url:String): Call<News>

}