package com.example.mynews.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.core.domain.model.News
import com.example.core.utlis.DateConverter
import com.example.mynews.R
import com.example.mynews.databinding.ActivityDetailNewsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailNewsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    private lateinit var binding: ActivityDetailNewsBinding
    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detail = intent.getParcelableExtra<News>(EXTRA_DATA)
        showDetailNews(detail)
    }

    private fun showDetailNews(detailNews: News?){
        detailNews.let {

            if (detailNews!!.urlToImage != null){
                Glide.with(this@DetailNewsActivity)
                    .load(detailNews.urlToImage)
                    .placeholder(com.example.core.R.drawable.loading)
                    .error(com.example.core.R.drawable.error)
                    .into(binding.tvDetailImage)
            }

            if (detailNews.title != null){
                binding.content.titleNews.text = detailNews.title
            } else {
                binding.content.titleNews.text = R.string.no_title.toString()
            }

            if(detailNews.author != null){
                binding.content.authorName.text = detailNews.author
            } else {
                binding.content.authorName.text = R.string.no_author.toString()
            }

            if (detailNews.publishedAt != null){
                binding.content.publishedAt.text = DateConverter.convertMillisToString(DateConverter.convertStringToMillis(detailNews.publishedAt))
            } else {
                binding.content.publishedAt.text = R.string.no_publishedAt.toString()
            }

            if (detailNews.content != null) {
                binding.content.content.text = detailNews.content
            } else {
                binding.content.content.text = R.string.no_content.toString()
            }

            val urlArticle = detailNews?.url
            if (urlArticle != null) {
                val spannableString = SpannableString(urlArticle)
                val clickableSpan = object : ClickableSpan() {
                    override fun onClick(view: View) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlArticle))
                        startActivity(intent)
                    }
                }
                spannableString.setSpan(clickableSpan, 0, urlArticle.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.content.link.text = spannableString
                binding.content.link.movementMethod = LinkMovementMethod.getInstance()
            } else {
                binding.content.link.text = R.string.no_link.toString()
            }

            var statusFavorite = detailNews.isFavorite
            statusFavorite(statusFavorite)
            binding.fab.setOnClickListener {
                statusFavorite = !statusFavorite
                detailViewModel.addFavorite(detailNews,statusFavorite)
                statusFavorite(statusFavorite)
            }
        }
    }

    private fun statusFavorite(statusFavorite:Boolean){
        if (statusFavorite) {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.favorite_full_star_white))
        } else {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.favorite_border_white))
        }
    }
}