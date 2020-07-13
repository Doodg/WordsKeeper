package com.enigma.wordskeeper.ui.addwords

import android.os.AsyncTask
import androidx.lifecycle.ViewModel
import com.enigma.wordskeeper.repository.model.WordModel
import com.enigma.wordskeeper.repository.WordsRepositoryImpl


class AddWordViewModel : ViewModel() {
    private var repository: WordsRepositoryImpl

    init {
        repository = WordsRepositoryImpl()
    }

    fun saveWord(wordModel: WordModel) {
        SaveWordAsyncTask().execute(wordModel)
    }


    fun deleteWord(id: Long) {
        DaveWordAsyncTask().execute(id)
    }


    private inner class SaveWordAsyncTask : AsyncTask<WordModel, Void?, Void?>() {
        override fun doInBackground(vararg params: WordModel): Void? {
            repository.saveWord(params[0])
            return null
        }
    }

    private inner class DaveWordAsyncTask : AsyncTask<Long, Void?, Void?>() {

        override fun doInBackground(vararg params: Long?): Void? {
            params[0]?.let { repository.removeWord(it) }
            return null
        }
    }
}