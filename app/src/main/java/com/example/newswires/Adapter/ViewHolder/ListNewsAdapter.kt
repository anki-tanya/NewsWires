package com.example.newswires.Adapter.ViewHolder

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.newswires.Interface.ItemClickListener
import com.example.newswires.Model.Article
import com.example.newswires.Model.News
import com.example.newswires.NewsDetails
import com.example.newswires.R
import com.example.newswires.common.ISO8601Parser
import com.google.gson.internal.bind.util.ISO8601Utils
import com.squareup.picasso.Picasso
import java.text.ParseException
import java.util.*

class ListNewsAdapter (val articleList : MutableList<Article>, private val context: Context):RecyclerView.Adapter<ListNewsViewHolder>() {


    override fun getItemCount(): Int {
        return articleList.size

    }


    override fun onBindViewHolder(holder: ListNewsViewHolder, position: Int) {


        Picasso.with(context)
            .load(articleList[position].urlToImage)
            .into(holder.articleImage)


        if(articleList[position].title!!.length>65)
        {
            holder.articleTitle.text = articleList[position].title!!.substring(0,65)+"..."
        }
        else
        {
            holder.articleTitle.text = articleList[position].title!!
        }

        if(articleList[position].publishedAt!=null)
        {
            var date:Date?=null
            try{
                date = ISO8601Parser.parse(articleList[position].publishedAt!!)
            }catch (ex:ParseException){
                ex.printStackTrace()
            }


            // Add Time
          //  holder.article_time.setReferenceTime(date!!.time)


        }

        holder.setItemClickListener(object : ItemClickListener {
            override fun onclick(view: View, position: Int) {

                val detail = Intent(context,NewsDetails::class.java)
                detail.putExtra("webURL",articleList[position].url)
                context.startActivity(detail)
            }
        })

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListNewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.news_layout,parent,false)
        return ListNewsViewHolder(itemView)
    }


}