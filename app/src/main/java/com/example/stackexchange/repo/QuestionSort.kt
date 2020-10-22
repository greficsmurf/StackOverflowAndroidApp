package com.example.stackexchange.repo

sealed class QuestionSort {
    abstract val title: String
    abstract val name: String
    companion object{
        val sortList = listOf<QuestionSort>(
//                Interesting(),
                Hot(),
                Week(),
                Month()
        )
    }

    data class Interesting(
            override val title: String = "Interesting",
            override val name: String = "creation"
    ) : QuestionSort()
    data class Hot(
            override val title: String = "Hot",
            override val name: String = "hot"
    ) : QuestionSort()
    data class Week(
            override val title: String = "Week",
            override val name: String = "week"
    ) : QuestionSort()
    data class Month(
            override val title: String = "Month",
            override val name: String = "month"
    ) : QuestionSort()
}

