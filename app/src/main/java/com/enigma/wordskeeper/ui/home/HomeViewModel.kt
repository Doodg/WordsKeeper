package com.enigma.wordskeeper.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.enigma.wordskeeper.repository.model.WordModel
import com.enigma.wordskeeper.repository.WordsRepositoryImpl

class HomeViewModel : ViewModel() {
    private var wordsRepository: WordsRepositoryImpl

    init {
        wordsRepository = WordsRepositoryImpl()
    }
    fun getListOfWords(): LiveData<PagedList<WordModel>> = wordsRepository.getWordsLiveData()

}