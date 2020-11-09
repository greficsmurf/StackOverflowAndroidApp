package com.example.stackexchange.db.dao

import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.stackexchange.db.models.BaseDbModel
import java.util.*

abstract class BaseDao <M> : IBaseDao<M> where M: BaseDbModel{
    suspend fun insertWithTimestamp(vararg data: M){
            insert(data.map { it.apply {
                Date().let { currentDate ->
                    createdAt = currentDate
                    updatedAt = currentDate
                }
            }
        })
    }
    suspend fun updateWithTimestamp(vararg data: M){
        update(data.map { it.apply {
            Date().let { currentDate ->
                    createdAt = currentDate
                    updatedAt = currentDate
                }
            }
        })
    }

    @RawQuery
    abstract suspend fun rawQuery(rawQuery: SupportSQLiteQuery): Int

    @Query("DELETE FROM sqlite_sequence WHERE name=:tableName")
    abstract suspend fun resetSequence(tableName: String)
}