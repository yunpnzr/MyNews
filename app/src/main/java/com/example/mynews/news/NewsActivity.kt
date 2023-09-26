package com.example.mynews.news

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.adapter.NewsAdapter
import com.example.mynews.databinding.ActivityNewsBinding
import com.example.mynews.detail.DetailNewsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    private val newsViewModel: NewsViewModel by viewModel{ parametersOf("us") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newsRecycler.layoutManager = LinearLayoutManager(this)

        val newsAdapter = NewsAdapter()
        binding.newsRecycler.adapter = newsAdapter

        newsViewModel.news.observe(this) { newsList ->
            newsAdapter.setDataNews(newsList)
        }

        newsAdapter.onItemClick = {selected ->
            val intent = Intent(this@NewsActivity, DetailNewsActivity::class.java)
            intent.putExtra(DetailNewsActivity.EXTRA_DATA,selected)
            startActivity(intent)
        }
    }
}