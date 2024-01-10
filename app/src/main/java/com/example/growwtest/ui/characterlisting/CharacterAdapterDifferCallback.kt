package com.example.growwtest.ui.characterlisting

import androidx.recyclerview.widget.DiffUtil
import com.example.growwtest.domain.model.Character

/*
 * Created by Sudhanshu Kumar on 11/01/24.
 */

class CharacterAdapterDifferCallback(
    private val oldList: List<Character>,
    private val newList: List<Character>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return oldList[oldPosition] == newList[newPosition]
    }

    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }

}