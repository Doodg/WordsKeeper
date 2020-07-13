package com.enigma.wordskeeper.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.enigma.wordskeeper.R
import com.enigma.wordskeeper.repository.model.WordModel
import com.enigma.wordskeeper.convertTOTimeFormat
import kotlinx.android.synthetic.main.word_list_item.view.*

class WordsListAdapter(
    private val onWordClickedListener: (itemPosition: Int, item: WordModel) -> Unit
) :
    PagedListAdapter<WordModel, WordsListAdapter.WordViewHolder>(WordsDiffCallback()) {


    override fun onBindViewHolder(holderPerson: WordViewHolder, position: Int) {
        val word = getItem(position)
        word?.let { holderPerson.bind(it) }
        holderPerson.itemView.setOnClickListener {
            word?.let { it1 -> onWordClickedListener(position, it1) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.word_list_item,
                parent, false
            )
        )
    }


    class WordViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(word: WordModel) {
            itemView.wordTextView.text = word.word
            itemView.wordCreationDateTextView.text =
                "Created at: ${word.creationDate.convertTOTimeFormat()}"
        }
    }
}