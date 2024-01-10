package com.example.growwtest.ui.characterlisting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.growwtest.databinding.ItemCharacterListingBinding
import com.example.growwtest.domain.model.Character

/*
 * Created by Sudhanshu Kumar on 23/12/23.
 */

class CharactersAdapter : RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {

    private val characters = mutableListOf<Character>()

    inner class CharacterViewHolder(val binding: ItemCharacterListingBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun setData(newCharacters: List<Character>?) {
        newCharacters?.let {
            val diffCallback = CharacterAdapterDifferCallback(characters, newCharacters)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            characters.addAll(newCharacters)
            diffResult.dispatchUpdatesTo(this)
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
        val person = characters[position]
        holder.binding.apply {
            tvNamePeopleListing.text = person.name
            root.setOnClickListener {
                onItemClickListener?.let { it(person) }
            }
        }
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    private var onItemClickListener: ((Character) -> Unit)? = null

    fun setOnItemClickListener(listener: (Character) -> Unit) {
        onItemClickListener = listener
    }
}