package org.dhis2.ui.calendar

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.mkb.ethiopian.lib.CalenderPickerFragment
import com.mkb.ethiopian.lib.listener.OnSelectListener
import java.text.DateFormat
import java.util.Calendar

object EthiopianDatePicker {
    fun show(fragmentManager: FragmentManager, context: Context, onDateSelected: (Long) -> Unit) {
        val calendarPickerFragment = CalenderPickerFragment.newInstance(
            openAt = Calendar.getInstance().timeInMillis,
            minDate = Calendar.getInstance().apply { add(Calendar.YEAR, -10) }.timeInMillis,
            maxDate = Calendar.getInstance().apply { add(Calendar.YEAR, 10) }.timeInMillis,
            primaryColor = ContextCompat.getColor(context, com.mkb.ethiopian.lib.R.color.colorPrimary),
            onSelectListener = object : OnSelectListener {
                override fun onDateSelect(date: Long) {
                    onDateSelected(date)
                    val dateString = DateFormat.format("dd-MM-yyyy", date)
                    Toast.makeText(context, "Ethiopian: $dateString", Toast.LENGTH_SHORT).show()
                }
            }
        )
        calendarPickerFragment.show(fragmentManager, "EthiopianCalendarPicker")
    }
}
