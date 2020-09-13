package com.example.newswires

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AlertDialog
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_news_details.*

class NewsDetails : AppCompatActivity() {

    lateinit var alertdialog: android.app.AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)
        alertdialog=SpotsDialog(this)
        alertdialog.show()

        webView.settings.javaScriptEnabled=true
        webView.webChromeClient= WebChromeClient()
        webView.webViewClient= object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                alertdialog.dismiss()
            }
        }


//        if(intent != null)
//            if(intent.getStringArrayExtra("webURL").isNotEmpty())
                webView.loadUrl(intent.getStringExtra("webURL"))
    }
}
