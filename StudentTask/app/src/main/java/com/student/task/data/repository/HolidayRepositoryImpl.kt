package com.student.task.data.repository

import com.student.task.data.datasource.HolidayLocalDataSource
import com.student.task.domain.model.Holiday
import com.student.task.domain.repository.HolidayRepository
import kotlinx.coroutines.delay

class HolidayRepositoryImpl(
    private val localDataSource: HolidayLocalDataSource
) : HolidayRepository {

    var simulateError: Boolean = false

    override suspend fun getHolidaysPage(page: Int, pageSize: Int): Result<List<Holiday>> {
        delay(SIMULATED_DELAY_MS)

        if (simulateError) {
            return Result.failure(
                Exception("Ошибка загрузки данных. Проверьте подключение к интернету.")
            )
        }

        val holidays = localDataSource.getPage(page, pageSize)
        return Result.success(holidays)
    }

    override suspend fun getTotalCount(): Int = localDataSource.getTotalCount()

    companion object {
        private const val SIMULATED_DELAY_MS = 1500L
    }
}
