package com.student.task.domain.usecase

import com.student.task.domain.model.Holiday
import com.student.task.domain.repository.HolidayRepository

class GetHolidaysPageUseCase(
    private val repository: HolidayRepository
) {
    suspend operator fun invoke(page: Int, pageSize: Int = PAGE_SIZE): Result<List<Holiday>> {
        return repository.getHolidaysPage(page, pageSize)
    }

    suspend fun getTotalCount(): Int = repository.getTotalCount()

    companion object {
        const val PAGE_SIZE = 5
    }
}
