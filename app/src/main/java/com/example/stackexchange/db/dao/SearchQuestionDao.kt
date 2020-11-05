package com.example.stackexchange.db.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.stackexchange.db.models.HotSearchQuestionDb
import com.example.stackexchange.db.models.IBaseSearchQuestionDb
import com.example.stackexchange.db.models.JoinSearchQuestionUserDb
import com.example.stackexchange.db.models.SearchQuestionDb
import com.example.stackexchange.repo.QuestionSort
import kotlinx.coroutines.flow.Flow

@Dao
abstract class SearchQuestionDao : BaseDao<SearchQuestionDb>(){
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    abstract suspend fun insert(list: List<SearchQuestionDb>)

    @Query("SELECT search.*, user.id AS u_id, " +
            "user.user_type AS u_user_type, " +
            "user.user_link AS u_user_link, " +
            "user.display_name AS u_display_name, " +
            "user.user_id AS u_user_id, " +
            "user.profile_image AS u_profile_image, " +
            "user.reputation AS u_reputation, " +
            "user.created_at AS u_created_at, " +
            "user.updated_at AS u_updated_at " +
            "FROM search_question AS search LEFT JOIN user ON owner_id=user.user_id WHERE sortId=:sortId")
    abstract fun getSortedQuestionDbFlow(sortId: Int): Flow<List<JoinSearchQuestionUserDb>>


    @Query("SELECT * FROM search_question")
    abstract suspend fun get(): List<SearchQuestionDb>

    @Query("DELETE FROM search_question WHERE sortId=:sortId")
    abstract suspend fun deleteBySortId(sortId: Int)

    @Query("SELECT * FROM search_question WHERE sortId=:sortId ORDER BY id")
    abstract fun getPagingSource(sortId: Int): PagingSource<Int, SearchQuestionDb>

    suspend fun deleteAndInsert(data: List<SearchQuestionDb>, sortId: Int){
        deleteBySortId(sortId)
        insert(data)
    }


}

