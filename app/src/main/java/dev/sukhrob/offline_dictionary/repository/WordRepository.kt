package dev.sukhrob.offline_dictionary.repository

import android.database.Cursor
import androidx.lifecycle.LiveData
import dev.sukhrob.offline_dictionary.data.WordDatabase
import dev.sukhrob.offline_dictionary.data.model.Word

class WordRepository {
    private val wordDao = WordDatabase.getInstance().getWordDao()

//    companion object {
//        private var wordRepository: WordRepository? = null
//        fun init() {
//            wordRepository = WordRepository()
//        }
//        fun getWordRepository() = wordRepository!!
//    }

    fun getWords(): LiveData<List<Word>> = wordDao.getAllWords()
    fun getWordsCursor(): Cursor = wordDao.getAllWordsCursor()
    fun getCursorBySearch(query: String): Cursor = wordDao.getCursorByQuery(query)
    fun getBookmarks(): Cursor = wordDao.getBookmarks()

    fun update(word: Word) = wordDao.update(word)

}