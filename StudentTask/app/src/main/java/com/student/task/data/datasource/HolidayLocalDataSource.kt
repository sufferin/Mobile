package com.student.task.data.datasource

import com.student.task.domain.model.Holiday
import com.student.task.domain.model.HolidayCategory

class HolidayLocalDataSource {

    private val holidays = listOf(
        Holiday(
            id = 1,
            name = "Новый год",
            date = "1 января 2026",
            month = 1, day = 1,
            description = "Главный праздник года. Новогодние каникулы длятся с 1 по 8 января. Традиционно украшают ёлку, дарят подарки и запускают фейерверки.",
            category = HolidayCategory.OFFICIAL,
            isOfficial = true
        ),
        Holiday(
            id = 2,
            name = "Рождество Христово",
            date = "7 января 2026",
            month = 1, day = 7,
            description = "Православное Рождество — один из главных христианских праздников, отмечаемый в России как государственный выходной день.",
            category = HolidayCategory.OFFICIAL,
            isOfficial = true
        ),
        Holiday(
            id = 3,
            name = "Татьянин день",
            date = "25 января 2026",
            month = 1, day = 25,
            description = "День российского студенчества. Назван в честь святой Татьяны. Традиционно студенты отмечают свой праздник.",
            category = HolidayCategory.FOLK,
            isOfficial = false
        ),
        Holiday(
            id = 4,
            name = "День святого Валентина",
            date = "14 февраля 2026",
            month = 2, day = 14,
            description = "День всех влюблённых. Неофициальный праздник, ставший популярным в России. Принято дарить валентинки и подарки.",
            category = HolidayCategory.INTERNATIONAL,
            isOfficial = false
        ),
        Holiday(
            id = 5,
            name = "День защитника Отечества",
            date = "23 февраля 2026",
            month = 2, day = 23,
            description = "Государственный праздник, посвящённый Вооружённым Силам России. Нерабочий праздничный день.",
            category = HolidayCategory.OFFICIAL,
            isOfficial = true
        ),
        Holiday(
            id = 6,
            name = "Международный женский день",
            date = "8 марта 2026",
            month = 3, day = 8,
            description = "Праздник, посвящённый женщинам. Государственный выходной день. Принято дарить цветы и подарки.",
            category = HolidayCategory.OFFICIAL,
            isOfficial = true
        ),
        Holiday(
            id = 7,
            name = "День театра",
            date = "27 марта 2026",
            month = 3, day = 27,
            description = "Всемирный день театра, учреждённый в 1961 году. В этот день проходят специальные спектакли и акции.",
            category = HolidayCategory.INTERNATIONAL,
            isOfficial = false
        ),
        Holiday(
            id = 8,
            name = "День смеха",
            date = "1 апреля 2026",
            month = 4, day = 1,
            description = "Неофициальный праздник юмора. Традиционно в этот день принято разыгрывать друзей и коллег.",
            category = HolidayCategory.FOLK,
            isOfficial = false
        ),
        Holiday(
            id = 9,
            name = "День космонавтики",
            date = "12 апреля 2026",
            month = 4, day = 12,
            description = "Памятная дата — в этот день в 1961 году Юрий Гагарин совершил первый в истории полёт в космос.",
            category = HolidayCategory.PROFESSIONAL,
            isOfficial = false
        ),
        Holiday(
            id = 10,
            name = "Праздник Весны и Труда",
            date = "1 мая 2026",
            month = 5, day = 1,
            description = "Государственный праздник, нерабочий день. Традиционно проводятся демонстрации и народные гуляния.",
            category = HolidayCategory.OFFICIAL,
            isOfficial = true
        ),
        Holiday(
            id = 11,
            name = "День Победы",
            date = "9 мая 2026",
            month = 5, day = 9,
            description = "Главный патриотический праздник страны — день победы в Великой Отечественной войне 1941–1945 годов. Проводятся парады и акция «Бессмертный полк».",
            category = HolidayCategory.OFFICIAL,
            isOfficial = true
        ),
        Holiday(
            id = 12,
            name = "День защиты детей",
            date = "1 июня 2026",
            month = 6, day = 1,
            description = "Международный день защиты детей. Проводятся праздничные мероприятия, концерты и конкурсы для детей.",
            category = HolidayCategory.INTERNATIONAL,
            isOfficial = false
        ),
        Holiday(
            id = 13,
            name = "День России",
            date = "12 июня 2026",
            month = 6, day = 12,
            description = "Государственный праздник Российской Федерации. Нерабочий день. Проводятся праздничные мероприятия и салюты.",
            category = HolidayCategory.OFFICIAL,
            isOfficial = true
        ),
        Holiday(
            id = 14,
            name = "День семьи, любви и верности",
            date = "8 июля 2026",
            month = 7, day = 8,
            description = "Российский праздник, приуроченный ко дню памяти святых Петра и Февронии. Символ праздника — ромашка.",
            category = HolidayCategory.FOLK,
            isOfficial = false
        ),
        Holiday(
            id = 15,
            name = "День Государственного флага",
            date = "22 августа 2026",
            month = 8, day = 22,
            description = "Памятная дата, посвящённая государственному триколору Российской Федерации — бело-сине-красному флагу.",
            category = HolidayCategory.OFFICIAL,
            isOfficial = false
        ),
        Holiday(
            id = 16,
            name = "День знаний",
            date = "1 сентября 2026",
            month = 9, day = 1,
            description = "Начало учебного года. Школьники и студенты идут на торжественные линейки. Традиционно дарят цветы учителям.",
            category = HolidayCategory.PROFESSIONAL,
            isOfficial = false
        ),
        Holiday(
            id = 17,
            name = "День учителя",
            date = "5 октября 2026",
            month = 10, day = 5,
            description = "Профессиональный праздник работников сферы образования. Ученики поздравляют своих наставников.",
            category = HolidayCategory.PROFESSIONAL,
            isOfficial = false
        ),
        Holiday(
            id = 18,
            name = "День народного единства",
            date = "4 ноября 2026",
            month = 11, day = 4,
            description = "Государственный праздник в честь освобождения Москвы от польских интервентов в 1612 году. Нерабочий день.",
            category = HolidayCategory.OFFICIAL,
            isOfficial = true
        ),
        Holiday(
            id = 19,
            name = "День матери",
            date = "29 ноября 2026",
            month = 11, day = 29,
            description = "Праздник, посвящённый матерям. Отмечается в последнее воскресенье ноября. Принято поздравлять мам и бабушек.",
            category = HolidayCategory.FOLK,
            isOfficial = false
        ),
        Holiday(
            id = 20,
            name = "Новогодняя ночь",
            date = "31 декабря 2026",
            month = 12, day = 31,
            description = "Главная ночь года! Бой курантов, загадывание желаний, оливье и мандарины — неизменные атрибуты праздника.",
            category = HolidayCategory.FOLK,
            isOfficial = false
        )
    )

    fun getAll(): List<Holiday> = holidays

    fun getPage(page: Int, pageSize: Int): List<Holiday> {
        val startIndex = page * pageSize
        if (startIndex >= holidays.size) return emptyList()
        val endIndex = minOf(startIndex + pageSize, holidays.size)
        return holidays.subList(startIndex, endIndex)
    }

    fun getTotalCount(): Int = holidays.size
}
