package com.example.growwtest.ui.characterlisting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.growwtest.databinding.ItemCharacterListingBinding
import com.example.growwtest.domain.model.Character
import com.example.growwtest.ui.filter.model.FeatureAttr

/*
 * Created by Sudhanshu Kumar on 23/12/23.
 */

class CharactersAdapter : RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {

    inner class CharacterViewHolder(val binding: ItemCharacterListingBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    fun submitData(newCharacters: List<Character>?) {
        newCharacters?.let {
            val newList = mutableListOf<Character>()
            newList.addAll(differ.currentList)
            newList.addAll(it)
            differ.submitList(newList)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            ItemCharacterListingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val person = differ.currentList[position]
        holder.binding.apply {
            tvNamePeopleListing.text = person.name
            tvEyeColorCharacterListing.text = "Eye color: ${person.eyeColor}"
            tvHairColorCharacterListing.text = "Hair color: ${person.hairColor}"
            tvGenderCharacterListing.text = "Gender: ${person.gender}"
            tvSkinColorCharacterListing.text = "Skin color: ${person.skinColor}"
            root.setOnClickListener {
                onItemClickListener?.let { it(person) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Character) -> Unit)? = null

    fun setOnItemClickListener(listener: (Character) -> Unit) {
        onItemClickListener = listener
    }
}