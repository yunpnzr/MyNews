package com.example.mynews.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mynews.R
import com.example.mynews.databinding.ActivityMainBinding
import com.example.mynews.news.NewsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.listNews.setOnClickListener {
            val intent = Intent(this@MainActivity, NewsActivity::class.java)
            startActivity(intent)
        }

        binding.favoriteNews.setOnClickListener {
            try{
                startActivity(Intent(this, Class.forName("com.example.favorite.main.FavoriteMainActivity")))
            } catch (e: Exception){
                Toast.makeText(this, getString(R.string.favorite_menu_not_found), Toast.LENGTH_SHORT).show()
            }
        }
    }
}