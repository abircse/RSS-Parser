package com.coxtunes.rssfeed

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coxtunes.rssfeedloadinraw.R
import com.coxtunes.rssfeedloadinraw.adapter.ArticleAdapter
import com.prof.rssparser.Parser
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.*


class MainActivity : AppCompatActivity() {


    lateinit var postAdapter: ArticleAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var okHttpClient: OkHttpClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.post_recyclerview)
        progressBar = findViewById(R.id.progressBar)
        okHttpClient = OkHttpClient()

        GlobalScope.launch(Dispatchers.IO) {
            val parser = Parser.Builder().build()
            val url = "https://seabeemagazine.navylive.dodlive.mil/feed/"

            GlobalScope.launch(Dispatchers.IO){
                async {
                    ////////////////////////////////////
                    val request = Request.Builder()
                        .url(url)
                        .build()
                    val result = okHttpClient.newCall(request).execute()
                    val raw = runCatching { result.body?.string() }.getOrNull()
                    if (raw == null) {
                        Toast.makeText(this@MainActivity, "Something went wrong!", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        val channel = parser.parse(raw)
                        MainScope().launch {
                            recyclerView.setHasFixedSize(true)
                            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                            postAdapter = ArticleAdapter(this@MainActivity,channel.articles)
                            recyclerView.adapter = postAdapter
                            progressBar.visibility = View.GONE
                            postAdapter.notifyDataSetChanged()
                        }
                    }
                    /////////////////////////////////////
                }.await()
            }

        }
    }
}


