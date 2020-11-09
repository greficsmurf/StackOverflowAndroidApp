package com.example.stackexchange.api.models

import com.squareup.moshi.Json

data class TagsApi(
        @Json(name = "items")
        val data: List<TagApi>
)