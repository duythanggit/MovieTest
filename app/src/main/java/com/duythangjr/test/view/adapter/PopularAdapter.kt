package com.duythangjr.test.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.duythangjr.test.databinding.ItemPopularBinding
import com.duythangjr.test.model.ResultPopular

class PopularAdapter : RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {
    inner class PopularViewHolder(val binding: ItemPopularBinding) :
        RecyclerView.ViewHolder(binding.root)


    private val differCallback = object : DiffUtil.ItemCallback<ResultPopular>() {
        override fun areItemsTheSame(oldItem: ResultPopular, newItem: ResultPopular): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ResultPopular, newItem: ResultPopular): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val binding = ItemPopularBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.binding.apply {
            // ko có base url của ảnh nên ko load đc
//            Glide.with(context).load(item.poster_path).into(imgThumbnail)

            tvTitle.text = item.original_title
            tvDate.text = item.release_date
            tvVoteAverage.text = item.vote_average.toString()
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}