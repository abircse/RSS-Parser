package com.coxtunes.rssfeedloadinraw.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.coxtunes.rssfeedloadinraw.MainActivity2
import com.coxtunes.rssfeedloadinraw.R
import com.prof.rssparser.Article
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class ArticleAdapter(val context: Context,val articles: MutableList<Article>) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false))

    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(articles[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title = itemView.findViewById<TextView>(R.id.title)
        private val pubDate = itemView.findViewById<TextView>(R.id.pubDate)
        private val categoriesText = itemView.findViewById<TextView>(R.id.categories)
        private val image = itemView.findViewById<ImageView>(R.id.image)

        @SuppressLint("SetJavaScriptEnabled")
        fun bind(article: Article) {

            var pubDateString = article.pubDate

            try {
                val sourceDateString = article.pubDate
                val sourceSdf = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
                if (sourceDateString != null) {
                    val date = sourceSdf.parse(sourceDateString)
                    if (date != null) {
                        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                        pubDateString = sdf.format(date)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            title.text = article.title

            Picasso.get()
                .load(article.image)
                .placeholder(R.drawable.placeholder)
                .into(image)

            pubDate.text = pubDateString

            var categories = ""
            for (i in 0 until article.categories.size) {
                categories = if (i == article.categories.size - 1) {
                    categories + article.categories[i]
                }
                else {
                    categories + article.categories[i] + ", "
                }
            }

            categoriesText.text = categories

            itemView.setOnClickListener {

                val intent = Intent(context,MainActivity2::class.java)
                intent.putExtra("data", article.content)
                context.startActivity(intent)
            }

        }
    }
}