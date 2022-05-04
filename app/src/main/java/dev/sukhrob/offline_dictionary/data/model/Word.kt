package dev.sukhrob.offline_dictionary.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val word: String,

    @ColumnInfo(name = "wordtype")
    val wordType: String,

    val definition: String,

    val isBookmark: Int = 0
)