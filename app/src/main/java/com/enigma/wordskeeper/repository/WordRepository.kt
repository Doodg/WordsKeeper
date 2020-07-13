package com.enigma.wordskeeper.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.enigma.wordskeeper.repository.model.WordModel

interface WordRepository{

    fun getWordsLiveData(): LiveData<PagedList<WordModel>>

    fun saveWord(wordModel: WordModel)

    fun removeWord(id: Long)

    fun deleteAllWords()
}