package com.example.stackexchange.interfaces

import androidx.navigation.NavController

interface QuestionsAdapterNavCallback {
    fun navigate(navController: NavController, url: String, title: String)
    fun navigateToUser(navController: NavController, id: Long)
    fun navigateToTagSearch(navController: NavController, tags: List<String>)
}