package com.example.growwtest.ui.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.growwtest.databinding.ItemFilterSortAttrBinding
import com.example.growwtest.ui.filter.model.FeatureAttr

/*
 * Created by Sudhanshu Kumar on 10/01/24.
 */

class FilterSortAttrAdapter : RecyclerView.Adapter<FilterSortAttrAdapter.FilterSortAttrViewHolder>() {

    inner class FilterSortAttrViewHolder(val binding: ItemFilterSortAttrBinding) :
            RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<FeatureAttr>() {
            override fun areItemsTheSame(oldItem: FeatureAttr, newItem: FeatureAttr): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: FeatureAttr, newItem: FeatureAttr): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    fun submitData(list: List<FeatureAttr>?) {
        list?.let {
            differ.submitList(null)
            differ.submitList(list)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterSortAttrViewHolder {
        return FilterSortAttrViewHolder(
            ItemFilterSortAttrBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FilterSortAttrViewHolder, position: Int) {
        val attr = differ.currentList[position]
        holder.binding.apply {
            tvTitleFilterAttr.text = attr.name
            cbFilterAttr.isChecked = attr.isChecked ?: false
            cbFilterAttr.setOnClickListener {
                attr.isChecked = !cbFilterAttr.isChecked
                onItemClickListener?.let { it(attr, position) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((FeatureAttr, position: Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (FeatureAttr, position: Int) -> Unit) {
        onItemClickListener = listener
    }

}