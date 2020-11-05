package com.example.stackexchange.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.example.stackexchange.db.models.HotSearchQuestionDb
import com.example.stackexchange.db.models.SearchQuestionDb
import com.example.stackexchange.db.models.WeekSearchQuestionDb

@Dao
abstract class WeekQuestionDao : BaseDao<WeekSearchQuestionDb>(){

    @Query("DELETE FROM week_search_question")
    abstract suspend fun delete()

    @Query("SELECT * FROM week_search_question ORDER BY id")
    abstract fun getPagingSource(): PagingSource<Int, WeekSearchQuestionDb>

    suspend fun deleteAndInsert(data: List<WeekSearchQuestionDb>){
        delete()
        resetSequence("week_search_question")
        insert(data)
    }

}