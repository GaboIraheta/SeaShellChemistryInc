package com.proyectoPdm.seashellinc.data.database

import android.app.Application
import androidx.room.Room

class InitDatabase : Application() {
    companion object{
        lateinit var database: CompoundDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            this,
            CompoundDatabase::class.java,
            "CompoundDatabase"
        ).fallbackToDestructiveMigration().build()
    }
}