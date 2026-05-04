package com.student.task.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.student.task.domain.model.HolidayCategory
import com.student.task.domain.usecase.GetHolidaysPageUseCase
import com.student.task.presentation.model.CardState
import com.student.task.presentation.model.HolidayUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HolidayViewModel @Inject constructor(
    private val getHolidaysPageUseCase: GetHolidaysPageUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Loading)
    val screenState: StateFlow<ScreenState> = _screenState.asStateFlow()

    private var currentPage = 0
    private var totalCount = 0
    private val allHolidays = mutableListOf<HolidayUiModel>()

    private val _selectedCategory = MutableStateFlow<HolidayCategory?>(null)
    val selectedCategory: StateFlow<HolidayCategory?> = _selectedCategory.asStateFlow()

    init {
        loadInitial()
    }

    private fun loadInitial() {
        viewModelScope.launch {
            _screenState.value = ScreenState.Loading
            totalCount = getHolidaysPageUseCase.getTotalCount()

            getHolidaysPageUseCase(page = 0).fold(
                onSuccess = { holidays ->
                    allHolidays.clear()
                    allHolidays.addAll(holidays.map { HolidayUiModel(it) })
                    currentPage = 0
                    updateState()
                },
                onFailure = { error ->
                    _screenState.value = ScreenState.Error(
                        message = error.message ?: "Неизвестная ошибка"
                    )
                }
            )
        }
    }

    fun loadNextPage() {
        val current = _screenState.value
        if (current !is ScreenState.Data || current.isLoadingMore || !current.hasMorePages) return

        viewModelScope.launch {
            _screenState.value = current.copy(isLoadingMore = true)

            getHolidaysPageUseCase(page = currentPage + 1).fold(
                onSuccess = { holidays ->
                    currentPage++
                    allHolidays.addAll(holidays.map { HolidayUiModel(it) })
                    updateState()
                },
                onFailure = { error ->
                    _screenState.value = current.copy(isLoadingMore = false)
                }
            )
        }
    }

    private fun updateState() {
        val filter = _selectedCategory.value
        val filteredHolidays = if (filter == null) {
            allHolidays
        } else {
            allHolidays.filter { it.holiday.category == filter }
        }

        _screenState.value = ScreenState.Data(
            holidays = filteredHolidays.toList(),
            isLoadingMore = false,
            hasMorePages = allHolidays.size < totalCount,
            currentPage = currentPage,
            selectedCategory = filter
        )
    }

    fun filterByCategory(category: HolidayCategory?) {
        _selectedCategory.value = category
        updateState()
    }

    fun retry() {
        loadInitial()
    }

    fun toggleCardState(holidayId: Int) {
        val index = allHolidays.indexOfFirst { it.holiday.id == holidayId }
        if (index != -1) {
            val uiModel = allHolidays[index]
            val newState = when (uiModel.cardState) {
                CardState.Default -> CardState.Expanded
                CardState.Expanded -> CardState.Default
                CardState.Favorite -> CardState.Favorite
            }
            allHolidays[index] = uiModel.copy(cardState = newState)
            updateState()
        }
    }

    fun toggleFavorite(holidayId: Int) {
        val index = allHolidays.indexOfFirst { it.holiday.id == holidayId }
        if (index != -1) {
            val uiModel = allHolidays[index]
            val newState = when (uiModel.cardState) {
                CardState.Favorite -> CardState.Default
                else -> CardState.Favorite
            }
            allHolidays[index] = uiModel.copy(cardState = newState)
            updateState()
        }
    }
}
