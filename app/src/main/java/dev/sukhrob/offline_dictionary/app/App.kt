package dev.sukhrob.offline_dictionary.app

import android.app.Application
import dev.sukhrob.offline_dictionary.data.WordDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        WordDatabase.init(this)
        //WordRepositoryImpl.init()
    }

}