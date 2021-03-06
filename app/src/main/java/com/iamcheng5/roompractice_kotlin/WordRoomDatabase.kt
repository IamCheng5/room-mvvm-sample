package com.iamcheng5.roompractice_kotlin

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Word::class], version = 1)
abstract class WordRoomDatabase:RoomDatabase() {
    abstract fun wordDao(): WordDao
    companion object{
        @Volatile
        private var INSTANCE:WordRoomDatabase?=null
        fun getDatabase(context: Context, scope:CoroutineScope):WordRoomDatabase{
                return INSTANCE?:synchronized(this){
                    val instance = Room.databaseBuilder(context.applicationContext,WordRoomDatabase::class.java,"word_database")
                        .fallbackToDestructiveMigration()
                        .addCallback(WordDatabaseCallback(scope))
                        .build()
                    INSTANCE = instance
                    instance
                }
        }
        private class WordDatabaseCallback(private val scope:CoroutineScope):RoomDatabase.Callback(){
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let {  database->
                    scope.launch(Dispatchers.IO) {
                        val wordDao:WordDao = database.wordDao()
                        wordDao.deleteAll()
                        wordDao.insert(Word("Hello"))
                        wordDao.insert(Word("World"))
                    }
                }
            }

        }

    }
}