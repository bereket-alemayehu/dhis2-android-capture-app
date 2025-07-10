package org.dhis2.commons.calendar

data class EthiopianDate(
    val year: Int,
    val month: Int,
    val day: Int
) {
    private val monthNames = arrayOf(
        "Meskerem", "Tikimt", "Hidar", "Tahsas", "Tir", "Yekatit",
        "Megabit", "Miyazya", "Ginbot", "Sene", "Hamle", "Nehase", "Pagumen"
    )

    fun getMonthName(): String {
        return if (month in 1..13) monthNames[month - 1] else "Invalid"
    }

    override fun toString(): String {
        return "$day ${getMonthName()} $year"
    }
}