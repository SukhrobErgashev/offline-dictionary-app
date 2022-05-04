package dev.sukhrob.offline_dictionary.data

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import dev.sukhrob.offline_dictionary.data.model.Word

@Dao
interface WordDao {
    @Query("SELECT * FROM words")
    fun getAllWords(): LiveData<List<Word>>

    @Query("SELECT * FROM words")
    fun getAllWordsCursor(): Cursor

    @Query("SELECT * FROM words WHERE word LIKE :query")
    fun getCursorByQuery(query: String): Cursor

    @Query("SELECT * FROM words WHERE isBookmark=1")
    fun getBookmarks(): Cursor

    @Update
    fun update(word: Word)
}