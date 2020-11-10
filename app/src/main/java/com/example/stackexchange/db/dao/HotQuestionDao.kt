package com.example.stackexchange.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.stackexchange.db.models.BaseSearchQuestionDb
import com.example.stackexchange.db.models.HotSearchQuestionDb
import com.example.stackexchange.db.models.SearchQuestionDb

@Dao
abstract class HotQuestionDao : BaseDao<HotSearchQuestionDb>(){

    @Query("DELETE FROM hot_search_question")
    abstract suspend fun delete()

    @Query("SELECT * FROM hot_search_question ORDER BY id")
    abstract fun getPagingSource(): PagingSource<Int, HotSearchQuestionDb>

    @Query("SELECT * FROM hot_search_question ORDER BY id LIMIT 1")
    abstract suspend fun getFirstItem(): HotSearchQuestionDb?

    suspend fun deleteAndInsert(data: List<HotSearchQuestionDb>){
        delete()
        resetSequence("hot_search_question")
        insertWithTimestamp(*data.toTypedArray())
    }

}