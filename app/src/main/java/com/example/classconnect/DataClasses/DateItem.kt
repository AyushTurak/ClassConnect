package com.example.classconnect.DataClasses

data class DateItem(
    val date: String,
    val students: List<String>,
    var isExpanded: Boolean = false // Keeps track of expanded/collapsed state
)
