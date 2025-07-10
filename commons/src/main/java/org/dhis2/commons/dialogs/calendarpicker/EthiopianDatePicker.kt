package org.dhis2.commons.dialogs.calendarpicker

import org.dhis2.commons.calendar.EthiopianDate
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.NumberPicker
import org.dhis2.commons.R

class EthiopianDatePicker(
    private val context: Context,
    private val listener: (ethiopianDate: EthiopianDate) -> Unit 
) {
    private var selectedYear = 2015
    private var selectedMonth = 1
    private var selectedDay = 1

    private val ETHIOPIAN_MONTHS = arrayOf(
        "Meskerem", "Tikimt", "Hidar", "Tahsas", "Tir", "Yekatit",
        "Megabit", "Miyazya", "Ginbot", "Sene", "Hamle", "Nehase", "Pagumen"
    )

    fun show() {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_ethiopian_datepicker, null)
        val yearPicker = view.findViewById<NumberPicker>(R.id.yearPicker)
        val monthPicker = view.findViewById<NumberPicker>(R.id.monthPicker)
        val dayPicker = view.findViewById<NumberPicker>(R.id.dayPicker)

        yearPicker.wrapSelectorWheel = true
        monthPicker.wrapSelectorWheel = true
        dayPicker.wrapSelectorWheel = true

        yearPicker.minValue = 1900
        yearPicker.maxValue = 2100
        yearPicker.value = selectedYear

        monthPicker.minValue = 1
        monthPicker.maxValue = 13
        monthPicker.displayedValues = ETHIOPIAN_MONTHS
        monthPicker.value = selectedMonth

        dayPicker.minValue = 1
        dayPicker.maxValue = getDaysInMonth(selectedYear, selectedMonth)
        dayPicker.value = selectedDay

        monthPicker.setOnValueChangedListener { _, _, newVal ->
            dayPicker.maxValue = getDaysInMonth(selectedYear, newVal)
            if (selectedDay > dayPicker.maxValue) {
                selectedDay = dayPicker.maxValue
                dayPicker.value = selectedDay
            }
            selectedMonth = newVal
        }

        yearPicker.setOnValueChangedListener { _, _, newVal ->
            dayPicker.maxValue = getDaysInMonth(newVal, selectedMonth)
            if (selectedDay > dayPicker.maxValue) {
                selectedDay = dayPicker.maxValue
                dayPicker.value = selectedDay
            }
            selectedYear = newVal
        }

        dayPicker.setOnValueChangedListener { _, _, newVal -> selectedDay = newVal }

        AlertDialog.Builder(context)
           .setTitle("Select Date")
            .setView(view)
            .setPositiveButton("OK") { _, _ ->
                // Return an EthiopianDate object
                listener(EthiopianDate(selectedYear, selectedMonth, selectedDay))
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun getDaysInMonth(year: Int, month: Int): Int {
        return when {
            month in 1..12 -> 30
            month == 13 -> if (isLeapYear(year)) 6 else 5
            else -> 30
        }
    }

    private fun isLeapYear(year: Int): Boolean {
        return year % 4 == 3
    }
}
