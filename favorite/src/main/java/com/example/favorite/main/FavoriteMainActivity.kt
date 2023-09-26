package com.example.favorite.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.adapter.NewsAdapter
import com.example.favorite.databinding.ActivityFavoriteMainBinding
import com.example.favorite.di.favoriteModule
import com.example.mynews.detail.DetailNewsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteMainBinding
    private val favoriteMainViewModel: FavoriteMainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(favoriteModule)

        binding.favoriteRecycler.layoutManager = LinearLayoutManager(this)

        val newsAdapter = NewsAdapter()
        binding.favoriteRecycler.adapter = newsAdapter

        favoriteMainViewModel.favoriteNews.observe(this) { newsList ->
            newsAdapter.setDataFavorite(newsList)
            val viewError =
                if (newsList.isNotEmpty()){
                    View.GONE
                } else {
                    View.VISIBLE
                }
            binding.viewError.root.visibility = viewError
        }

        newsAdapter.onItemClick = {selected ->
            val intent = Intent(this@FavoriteMainActivity, DetailNewsActivity::class.java)
            intent.putExtra(DetailNewsActivity.EXTRA_DATA,selected)
            startActivity(intent)
        }
    }
}