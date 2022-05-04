package dev.sukhrob.offline_dictionary.viewmodel

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.sukhrob.offline_dictionary.data.model.Word
import dev.sukhrob.offline_dictionary.repository.WordRepository

class ListViewModel: ViewModel() {
    private val repository = WordRepository()

    val wordList: LiveData<List<Word>> = repository.getWords()
    val bookmarks: MutableLiveData<Cursor> = MutableLiveData()
    val cursorWordList: MutableLiveData<Cursor> = MutableLiveData()

    fun loadWords() {
        cursorWordList.value = repository.getWordsCursor()
    }

    fun search(query: String) {
        if (query.trim().isNotEmpty())
            cursorWordList.value = repository.getCursorBySearch("%$query%")
    }

    fun getBookmarks() {
        bookmarks.value = repository.getBookmarks()
    }

    fun update(word: Word) {
        val new = Word(
            word.id,
            word.word,
            word.wordType,
            word.definition,
            if (word.isBookmark == 0) 1 else 0
        )
        repository.update(
            new
        )
    }
}