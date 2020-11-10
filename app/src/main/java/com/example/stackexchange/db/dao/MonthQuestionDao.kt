package com.example.stackexchange.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.example.stackexchange.db.models.HotSearchQuestionDb
import com.example.stackexchange.db.models.MonthSearchQuestionDb
import com.example.stackexchange.db.models.SearchQuestionDb

@Dao
abstract class MonthQuestionDao : BaseDao<MonthSearchQuestionDb>(){
    @Query("DELETE FROM month_search_question")
    abstract suspend fun delete()

    @Query("SELECT * FROM month_search_question ORDER BY id")
    abstract fun getPagingSource(): PagingSource<Int, MonthSearchQuestionDb>

    @Query("SELECT * FROM month_search_question ORDER BY id LIMIT 1")
    abstract suspend fun getFirstItem(): MonthSearchQuestionDb?

    suspend fun deleteAndInsert(data: List<MonthSearchQuestionDb>){
        delete()
        resetSequence("month_search_question")
        insertWithTimestamp(*data.toTypedArray())
    }
}