package com.enigma.wordskeeper.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.enigma.wordskeeper.WordModel
import com.enigma.wordskeeper.data.WordsRepositoryImpl

class HomeViewModel : ViewModel() {
    private var wordsRepository: WordsRepositoryImpl

    init {
        wordsRepository = WordsRepositoryImpl()
    }
    fun getListOfWords(): LiveData<PagedList<WordModel>> = wordsRepository.getWordsLiveData()

}