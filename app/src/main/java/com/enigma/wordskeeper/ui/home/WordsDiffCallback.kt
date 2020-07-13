package com.enigma.wordskeeper.ui.home

import androidx.recyclerview.widget.DiffUtil
import com.enigma.wordskeeper.repository.model.WordModel

class WordsDiffCallback : DiffUtil.ItemCallback<WordModel>() {

    override fun areItemsTheSame(oldItem: WordModel, newItem: WordModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: WordModel, newItem: WordModel): Boolean {
        return oldItem == newItem
    }
}