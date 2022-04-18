package com.iamcheng5.roompractice_kotlin

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Query("SELECT * FROM word_table ORDER BY WORD ASC")
    fun getAlphabetizedWordsASC(): Flow<List<Word>>

    /*@ExperimentalCoroutinesApi
    fun getAllDistinctUntilChanged() = getAlphabetizedWordsASC().distinctUntilChanged()*/

    @Query("SELECT * FROM word_table ORDER BY WORD DESC")
    fun getAlphabetizedWordsDESC(): Flow<List<Word>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    @Delete
    suspend fun delete(word: Word)

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()


}