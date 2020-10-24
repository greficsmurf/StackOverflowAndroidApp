package com.example.stackexchange.db.models

import androidx.room.*


data class JoinSearchQuestionUserDb(
        @Embedded val searchQuestion: SearchQuestionDb,
        @Embedded val user: UserDb
)