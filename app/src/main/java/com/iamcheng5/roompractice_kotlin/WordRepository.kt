package com.iamcheng5.roompractice_kotlin

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class WordRepository(private val wordDao: WordDao) {

    val allWordsASC: Flow<List<Word>> =wordDao.getAlphabetizedWordsASC()
    val allWordsDESC: Flow<List<Word>> =wordDao.getAlphabetizedWordsDESC()

    @WorkerThread
    suspend fun  delete(word:Word){
        wordDao.delete(word)
    }

    @WorkerThread
    suspend fun insert(word:Word){
        wordDao.insert(word)
    }
}