package com.example.stackexchange.api.models

data class TagApi(
        val hasSynonyms: Boolean = false,
        val isModeratorOnly: Boolean = false,
        val isRequired: Boolean = false,
        val count: Long = 0,
        val name: String = "unrecognized tag"
)