package com.enigma.wordskeeper

import android.app.Application
import com.facebook.stetho.Stetho

class WordsKeeperApp :Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        setupStetcho()
    }

    private fun setupStetcho() {
        if (BuildConfig.DEBUG) Stetho.initializeWithDefaults(this)
    }
    companion object {
        private var instance: WordsKeeperApp? = null
        fun getInstance(): WordsKeeperApp {
            if (instance == null)
                throw IllegalStateException("Something went horribly wrong!!, no application attached!")
            return instance as WordsKeeperApp
        }


    }
}