package org.dhis2.commons.calendar

object DateConverter {

    private val ethMonths = intArrayOf(30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 5)

    fun gregorianToEthiopian(gYear: Int, gMonth: Int, gDay: Int): EthiopianDate {
        val newYearDay = if (gYear % 4 == 3) 12 else 11

        var ethYear = gYear - 8
        var ethMonth: Int
        val ethDay: Int

        if (gMonth < 9 || (gMonth == 9 && gDay < newYearDay)) {
            ethYear -= 1
        }

        val totalGDays = getTotalDays(gYear, gMonth, gDay)
        var totalEthDays = totalGDays - getTotalDays(gYear, 9, newYearDay) + 1

        if (totalEthDays <= 0) {
            totalEthDays += if (isLeapYear(ethYear)) 366 else 365
        }

        var dayCount = 0
        for (i in 0..12) {
            val monthLength = if (i == 12 && isLeapYear(ethYear)) 6 else ethMonths[i]
            if (totalEthDays <= dayCount + monthLength) {
                ethMonth = i + 1
                ethDay = totalEthDays - dayCount
                return EthiopianDate(ethYear, ethMonth, ethDay)
            }
            dayCount += monthLength
        }

        return EthiopianDate(ethYear, 1, 1) // fallback
    }

    private fun getTotalDays(year: Int, month: Int, day: Int): Int {
        val daysInMonth = intArrayOf(0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334)
        var total = (year - 1) * 365 + (year - 1) / 4
        total += daysInMonth[month - 1] + day
        if (month > 2 && isLeapYear(year)) total += 1
        return total
    }

    private fun isLeapYear(year: Int): Boolean {
        return year % 4 == 3
    }
}
