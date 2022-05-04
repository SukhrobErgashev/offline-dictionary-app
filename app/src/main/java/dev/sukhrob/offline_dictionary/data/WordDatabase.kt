package dev.sukhrob.offline_dictionary.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.sukhrob.offline_dictionary.data.model.Word

@Database(
    entities = [Word::class],
    version = 1
)
abstract class WordDatabase : RoomDatabase() {

    abstract fun getWordDao(): WordDao

    companion object {
        private var instance: WordDatabase? = null

        fun init(application: Application) {
            instance = Room.databaseBuilder(
                application.applicationContext,
                WordDatabase::class.java,
                "data.db"
            )
                .createFromAsset("dictionary.db")
                .allowMainThreadQueries()
                .build()
        }

        fun getInstance() = instance!!
    }
}