package com.example.growwtest.ui.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.growwtest.R
import com.example.growwtest.databinding.ItemFilterSortBinding
import com.example.growwtest.domain.model.CharacterFeature

/*
 * Created by Sudhanshu Kumar on 10/01/24.
 */

class FilterAdapter : RecyclerView.Adapter<FilterAdapter.FilterSortViewHolder>() {

    private var selectedIndex: Int? = null

    inner class FilterSortViewHolder(val binding: ItemFilterSortBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<CharacterFeature>() {
        override fun areItemsTheSame(oldItem: CharacterFeature, newItem: CharacterFeature): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CharacterFeature, newItem: CharacterFeature): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterSortViewHolder {
        return FilterSortViewHolder(
            ItemFilterSortBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FilterSortViewHolder, position: Int) {
        val feature = differ.currentList[position]
        holder.binding.apply {
            tvTitleFilter.text = feature.id
            if(selectedIndex == position) {
                root.setBackgroundColor(root.context.getColor(R.color.selected_bg))
                tvTitleFilter.setTextColor(root.context.getColor(R.color.white))
            } else {
                root.setBackgroundColor(root.context.getColor(R.color.white))
                tvTitleFilter.setTextColor(root.context.getColor(R.color.black))
            }
            root.setOnClickListener {
                selectedIndex = position
                onItemClickListener?.let { it(feature, position) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((CharacterFeature, position: Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (CharacterFeature, position: Int) -> Unit) {
        onItemClickListener = listener
    }
}