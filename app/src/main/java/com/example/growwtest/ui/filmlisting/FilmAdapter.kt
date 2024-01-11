package com.example.growwtest.ui.filmlisting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.growwtest.databinding.ItemFilmListingBinding
import com.example.growwtest.domain.model.FilmUI

/*
 * Created by Sudhanshu Kumar on 11/01/24.
 */

class FilmAdapter : RecyclerView.Adapter<FilmAdapter.FilmViewHolder>() {

    inner class FilmViewHolder(val binding: ItemFilmListingBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<FilmUI>() {
        override fun areItemsTheSame(oldItem: FilmUI, newItem: FilmUI): Boolean {
            return oldItem.episodeId == newItem.episodeId
        }

        override fun areContentsTheSame(oldItem: FilmUI, newItem: FilmUI): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        return FilmViewHolder(
            ItemFilmListingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = differ.currentList[position]
        holder.binding.apply {
            tvNameFilmListing.text = film.title
            root.setOnClickListener {
                onItemClickListener?.let { it(film, position) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((FilmUI, position: Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (FilmUI, position: Int) -> Unit) {
        onItemClickListener = listener
    }
}