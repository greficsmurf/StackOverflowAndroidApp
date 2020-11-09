package com.example.stackexchange.domain.models

data class Tag(
        val hasSynonyms: Boolean,
        val isModeratorOnly: Boolean,
        val isRequired: Boolean,
        val count: Long,
        val name: String
)