package com.example.newswires.common

import com.example.newswires.Interface.NewsService
import com.example.newswires.Remote.RetrofitClient

object Common{
    val BASE_URL = "https://newsapi.org/"
    val API_KEY = "20bf7227cef04b9c81daff4c9c2e71e3"

    val newsService:NewsService
        get() = RetrofitClient.getClient(BASE_URL)!!.create(NewsService::class.java)

    fun getNewsAPI(source:String):String{

        return StringBuilder("https://newsapi.org/v2/top-headlines?sources=").append(source).append("&apiKey=")
            .append(API_KEY).toString()
    }

}