package com.example.stackexchange.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stackexchange.db.models.JoinSearchQuestionUserDb
import com.example.stackexchange.db.models.SearchQuestionDb
import com.example.stackexchange.repo.QuestionSort
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchQuestionDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<SearchQuestionDb>)

    @Query("SELECT *, user.* FROM search_question LEFT JOIN user ON owner_id=user.user_id WHERE sortId=:sortId")
    fun getSortedQuestionDb(sortId: Int): Flow<List<JoinSearchQuestionUserDb>>

}