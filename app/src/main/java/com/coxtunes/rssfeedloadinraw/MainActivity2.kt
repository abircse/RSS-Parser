package com.coxtunes.rssfeedloadinraw

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.HtmlCompat

class MainActivity2 : AppCompatActivity() {

    lateinit var webView: WebView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        webView = findViewById(R.id.webview)
        webView.settings.loadWithOverviewMode = true
        webView.settings.javaScriptEnabled = true
        webView.isHorizontalScrollBarEnabled = false
        webView.webChromeClient = WebChromeClient()
        val content = intent.getStringExtra("data")
        webView.loadDataWithBaseURL(null, "<style>img{display: inline; height: auto; max-width: 100%;} " + "</style>\n" + "<style>iframe{ height: auto; width: auto;}" + "</style>\n" + content, null, "utf-8", null)

    }
}
