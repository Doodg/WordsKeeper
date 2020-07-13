package com.enigma.wordskeeper.repository.database

import androidx.paging.DataSource
import androidx.room.*
import com.enigma.wordskeeper.repository.model.WordModel


@Dao
interface WordsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveWord(wordModel: WordModel)

    @Query("DELETE FROM WordModel where id=:id")
    fun removeWord(id: Long)

    @Query("DELETE FROM WordModel")
    fun deleteAll()

    @Query("SELECT * from WordModel ORDER BY creationDate DESC")
    fun getAllWords(): DataSource.Factory<Int, WordModel>

    @Query("SELECT * from WordModel WHERE creationDate=:creationDate")
    fun getWord(creationDate: Long): WordModel


}