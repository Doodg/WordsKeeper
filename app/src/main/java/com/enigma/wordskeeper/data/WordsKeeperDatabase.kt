package com.enigma.wordskeeper.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.enigma.wordskeeper.WordModel

@Database(entities = arrayOf(WordModel::class), version = 2
    , exportSchema = false)
abstract class WordsKeeperDatabase : RoomDatabase() {
    abstract fun wordDao(): WordsDao


    companion object {
        private var roomInstance: WordsKeeperDatabase? = null
        fun getInstance(context: Context): WordsKeeperDatabase {
            if (roomInstance == null) {
                roomInstance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        WordsKeeperDatabase::class.java, "words_keeper"

                    ).fallbackToDestructiveMigration()
                        .build()
            }
            return roomInstance as WordsKeeperDatabase
        }
    }
}