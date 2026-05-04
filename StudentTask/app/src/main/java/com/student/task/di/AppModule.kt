package com.student.task.di

import com.student.task.data.datasource.HolidayLocalDataSource
import com.student.task.data.repository.HolidayRepositoryImpl
import com.student.task.domain.repository.HolidayRepository
import com.student.task.domain.usecase.GetHolidaysPageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHolidayLocalDataSource(): HolidayLocalDataSource {
        return HolidayLocalDataSource()
    }

    @Provides
    @Singleton
    fun provideHolidayRepository(
        localDataSource: HolidayLocalDataSource
    ): HolidayRepository {
        return HolidayRepositoryImpl(localDataSource)
    }

    @Provides
    @Singleton
    fun provideGetHolidaysPageUseCase(
        repository: HolidayRepository
    ): GetHolidaysPageUseCase {
        return GetHolidaysPageUseCase(repository)
    }
}
