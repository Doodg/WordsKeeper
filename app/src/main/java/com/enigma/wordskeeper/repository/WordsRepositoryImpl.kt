package com.enigma.wordskeeper.repository

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.enigma.wordskeeper.WordsKeeperApp
import com.enigma.wordskeeper.repository.database.WordsKeeperDatabase
import com.enigma.wordskeeper.repository.model.WordModel

class WordsRepositoryImpl :WordRepository{
    private var wordsLiveData: LiveData<PagedList<WordModel>>
    private val wordsKeeperDatabase by lazy {
        WordsKeeperDatabase.getInstance(WordsKeeperApp.getInstance())
    }

    init {
        val factory: DataSource.Factory<Int, WordModel> =
            wordsKeeperDatabase.wordDao().getAllWords()

        val pagedListBuilder: LivePagedListBuilder<Int, WordModel> =
            LivePagedListBuilder(
                factory,
                5
            )
        wordsLiveData = pagedListBuilder.build()
    }

    override fun getWordsLiveData() = wordsLiveData

    override fun saveWord(wordModel: WordModel) {
        wordsKeeperDatabase.wordDao().saveWord(wordModel)
    }

    override fun removeWord(id: Long) {
        wordsKeeperDatabase.wordDao().removeWord(id)
    }

    override fun deleteAllWords() {
        wordsKeeperDatabase.wordDao().deleteAll()

    }
}