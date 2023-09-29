package com.example.core.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.R
import com.example.core.data.source.Resource
import com.example.core.databinding.ItemNewsBinding
import com.example.core.domain.model.News
import com.example.core.utlis.DateConverter

class NewsAdapter() : RecyclerView.Adapter<NewsAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = newsList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val binding =  ItemNewsBinding.bind(itemView)
        fun bind(news: News){
            with(binding){
                if (news.urlToImage != null){
                    Glide.with(itemView.context)
                        .load(news.urlToImage)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.error)
                        .into(tvNews)
                } else {
                    Glide.with(itemView.context)
                        .load(R.drawable.error)
                        .into(tvNews)
                }
                headlineNews.text = news.title
                time.text = DateConverter.convertMillisToString(DateConverter.convertStringToMillis(news.publishedAt))
                contentNews.text = news.content.toString().orEmpty()
            }
        }
        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(newsList[adapterPosition])
            }
        }
    }

    private var newsList = ArrayList<News>()
    var onItemClick: ((News) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setDataFavorite(newListData: List<News>?) {
        if (newListData == null) return
        newsList.clear()
        newsList.addAll(newListData)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDataNews(resource: Resource<List<News>>?) {
        if (resource is Resource.Success) {
            val newsList = resource.data
            newsList?.let {
                this.newsList.clear()
                this.newsList.addAll(it)
                notifyDataSetChanged()
                //val startPosition = this.newsList.size
                //this.newsList.addAll(it)
                //notifyItemRangeInserted(startPosition, it.size)
            }
        }
    }
}