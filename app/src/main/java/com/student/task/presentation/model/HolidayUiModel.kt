package com.student.task.presentation.model

import com.student.task.domain.model.Holiday

data class HolidayUiModel(
    val holiday: Holiday,
    val cardState: CardState = CardState.Default
)
