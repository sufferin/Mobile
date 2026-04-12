# StudentTask — Праздники России 2026

Учебный Android-проект для практики UI/UX верстки. Студенты работают с готовой архитектурой и дописывают недостающие UI-состояния.

## Что это?

Приложение отображает список праздников России на 2026 год с пагинацией. Архитектура полностью готова — студенту нужно реализовать только UI-часть.

**Реализовано:**
- Clean Architecture (domain / data / presentation / ui)
- MVVM с ViewModel и StateFlow
- Hilt DI
- Kotlin Coroutines
- Пагинация (по 5 элементов)
- Data-состояние экрана и Default-состояние карточки

**Не реализовано (задания):**
- Loading и Error состояния экрана
- Expanded и Favorite состояния карточки
- Индикатор подгрузки при пагинации
- Pull-to-refresh, фильтрация, пустое состояние

## Два варианта верстки

Проект поддерживает два подхода к UI — переключение через флаг в `MainActivity.kt`:

```kotlin
private const val USE_COMPOSE = true   // Jetpack Compose
private const val USE_COMPOSE = false  // XML + ViewBinding
```

| Compose | XML |
|---------|-----|
| `ui/compose/HolidayComposeScreen.kt` | `ui/xml/HolidayXmlFragment.kt` |
| `ui/compose/HolidayCard.kt` | `ui/xml/HolidayAdapter.kt` |
| Material3 Composable | RecyclerView + CardView + ViewBinding |

## Где читать задания?

Все задания подробно описаны в файле:

**[`app/src/main/java/com/student/task/Task.kt`](app/src/main/java/com/student/task/Task.kt)**

Там указаны файлы для редактирования, требования и критерии оценки для каждого задания.

## Стек

- Kotlin 2.1.0
- Jetpack Compose (Material3) / XML (ViewBinding, RecyclerView, CardView)
- ViewModel + StateFlow + Coroutines
- Hilt (Dependency Injection)
- Clean Architecture
- AGP 8.7.3, compileSdk 35, minSdk 28

## Структура проекта

```
com.student.task/
├── App.kt                          — Application (Hilt)
├── MainActivity.kt                 — Точка входа, флаг USE_COMPOSE
├── Task.kt                         — Описание всех заданий
├── di/AppModule.kt                 — Hilt-модуль
├── domain/
│   ├── model/Holiday.kt            — Модель праздника
│   ├── model/HolidayCategory.kt    — Категории
│   ├── repository/HolidayRepository.kt
│   └── usecase/GetHolidaysPageUseCase.kt
├── data/
│   ├── datasource/HolidayLocalDataSource.kt  — 20 праздников
│   └── repository/HolidayRepositoryImpl.kt   — Имитация сети
├── presentation/
│   ├── HolidayViewModel.kt         — ViewModel с пагинацией
│   ├── ScreenState.kt              — Loading / Error / Data
│   └── model/
│       ├── CardState.kt            — Default / Expanded / Favorite
│       └── HolidayUiModel.kt
└── ui/
    ├── compose/
    │   ├── HolidayComposeScreen.kt — Compose-экран
    │   └── HolidayCard.kt          — Compose-карточка
    └── xml/
        ├── HolidayXmlFragment.kt   — XML Fragment
        └── HolidayAdapter.kt       — RecyclerView Adapter
```

## Тестирование Error-состояния

В `HolidayRepositoryImpl.kt` установите `simulateError = true` для имитации ошибки загрузки.
