package com.enigma.wordskeeper.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.enigma.wordskeeper.WordModel
import com.enigma.wordskeeper.WordsKeeperApp

class WordsRepository {
    private var wordsLiveData: LiveData<PagedList<WordModel>>
    private val wordsKeeperDatabase by lazy {
        WordsKeeperDatabase.getInstance(WordsKeeperApp.getInstance())
    }

    init {
        val factory: DataSource.Factory<Int, WordModel> =
            wordsKeeperDatabase.wordDao().getAllWords()

        val pagedListBuilder: LivePagedListBuilder<Int, WordModel> =
            LivePagedListBuilder<Int, WordModel>(
                factory,
                5
            )
        wordsLiveData = pagedListBuilder.build()
    }

    fun getWordsLiveData() = wordsLiveData

    fun saveWord(wordModel: WordModel) {
        wordsKeeperDatabase.wordDao().saveWord(wordModel)
    }

    fun removeWord(id: Long) {
        wordsKeeperDatabase.wordDao().removeWord(id)
    }

    fun deleteAllWords() {
        wordsKeeperDatabase.wordDao().deleteAll()

    }
}