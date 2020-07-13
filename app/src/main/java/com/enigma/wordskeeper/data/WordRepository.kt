package com.enigma.wordskeeper.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.enigma.wordskeeper.WordModel

interface WordRepository{

    fun getWordsLiveData(): LiveData<PagedList<WordModel>>

    fun saveWord(wordModel: WordModel)

    fun removeWord(id: Long)

    fun deleteAllWords()
}