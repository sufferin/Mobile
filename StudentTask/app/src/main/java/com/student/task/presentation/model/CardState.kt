package com.student.task.presentation.model

enum class CardState {
    /** Базовое состояние — название, дата, категория. UI реализован. */
    Default,

    /** Развёрнутое состояние — показывает полное описание праздника. */
    Expanded,

    /** Избранное — визуально выделенная карточка. */
    Favorite
}
