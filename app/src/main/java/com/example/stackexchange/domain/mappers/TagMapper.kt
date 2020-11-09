package com.example.stackexchange.domain.mappers

import com.example.stackexchange.api.models.TagApi
import com.example.stackexchange.db.models.TagDb
import com.example.stackexchange.domain.models.Tag

fun TagApi.toDomainModel() = Tag(
        hasSynonyms,
        isModeratorOnly,
        isRequired,
        count,
        name
)

fun Tag.toDbModel() = TagDb(
        hasSynonyms,
        isModeratorOnly,
        isRequired,
        count,
        name
)

fun TagDb.toDomainModel() = Tag(
        hasSynonyms,
        isModeratorOnly,
        isRequired,
        count,
        name
)