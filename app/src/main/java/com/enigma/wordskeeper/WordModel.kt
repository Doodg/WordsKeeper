package com.enigma.wordskeeper

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity()
data class WordModel(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var word: String,
    var creationDate: Long,
    var openWordAt: Long = 0L,
    val isWordOpened:Boolean=false
) : Serializable