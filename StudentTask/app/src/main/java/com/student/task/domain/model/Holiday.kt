package com.student.task.domain.model

data class Holiday(
    val id: Int,
    val name: String,
    val date: String,
    val month: Int,
    val day: Int,
    val description: String,
    val category: HolidayCategory,
    val isOfficial: Boolean
)
