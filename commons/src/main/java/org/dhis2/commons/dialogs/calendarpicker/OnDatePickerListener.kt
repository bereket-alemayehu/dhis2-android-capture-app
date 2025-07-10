package org.dhis2.commons.dialogs.calendarpicker

interface OnDatePickerListener {
    fun onNegativeClick()
    fun onPositiveClick(year: Int, month: Int, day: Int)
}
