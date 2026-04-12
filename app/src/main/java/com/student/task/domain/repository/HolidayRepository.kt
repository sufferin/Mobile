package com.student.task.domain.repository

import com.student.task.domain.model.Holiday

interface HolidayRepository {
    suspend fun getHolidaysPage(page: Int, pageSize: Int): Result<List<Holiday>>
    suspend fun getTotalCount(): Int
}
