package com.jaideep.expensetracker.presentation.utility

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import com.jaideep.expensetracker.R
import com.jaideep.expensetracker.presentation.theme.black
import com.jaideep.expensetracker.presentation.theme.blue
import com.jaideep.expensetracker.presentation.theme.brown
import com.jaideep.expensetracker.presentation.theme.darkGray
import com.jaideep.expensetracker.presentation.theme.green
import com.jaideep.expensetracker.presentation.theme.orange
import com.jaideep.expensetracker.presentation.theme.pink
import com.jaideep.expensetracker.presentation.theme.purple
import com.jaideep.expensetracker.presentation.theme.red
import com.jaideep.expensetracker.presentation.theme.seed
import com.jaideep.expensetracker.presentation.theme.yellow
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeParseException

object Utility {
    fun getCategoryIconName(categoryId: Int): String {
        return when (categoryId) {
            R.drawable.food -> "Food"
            R.drawable.fuel -> "Fuel"
            R.drawable.travel -> "Travel"
            R.drawable.entertainment -> "Entertainment"
            R.drawable.shopping -> "Shopping"
            R.drawable.hospital -> "Hospital"
            R.drawable.mobile_recharge -> "Mobile Recharge"
            R.drawable.salary -> "Salary"
            R.drawable.medicine -> "Medicine"
            R.drawable.electricity -> "Electricity Bill"
            else -> "Other"
        }
    }

    fun getTrackColor(categoryName: String): Color {
        return when (categoryName) {
            "Food" -> red
            "Fuel" -> darkGray
            "Entertainment" -> green
            "Shopping" -> yellow
            "Travel" -> orange
            "Hospital" -> purple
            "Mobile Recharge" -> pink
            "Salary" -> blue
            "Medicine" -> seed
            "Electricity Bill" -> brown
            else -> black
        }
    }

    fun getCategoryIconId(iconName: String): Int {
        return when (iconName) {
            "Food" -> R.drawable.food
            "Fuel" -> R.drawable.fuel
            "Entertainment" -> R.drawable.entertainment
            "Shopping" -> R.drawable.shopping
            "Travel" -> R.drawable.travel
            "Hospital" -> R.drawable.hospital
            "Mobile Recharge" -> R.drawable.mobile_recharge
            "Salary" -> R.drawable.salary
            "Medicine" -> R.drawable.medicine
            "Electricity Bill" -> R.drawable.electricity
            else -> R.drawable.category
        }
    }

    fun getDateInMillis(date: LocalDate): Long {
        return date.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
    }

    fun getCurrentDateInMillis(): Long {
        return LocalDate.now().atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
    }

    fun getStartDateOfMonthInMillis(): Long {
        return LocalDate.now().atStartOfDay(ZoneOffset.UTC).withDayOfMonth(1).toInstant()
            .toEpochMilli()
    }

    fun getStartDateOfYearInMillis(): Long {
        return LocalDate.now().atStartOfDay(ZoneOffset.UTC).withDayOfYear(1).toInstant()
            .toEpochMilli()
    }

    fun stringDateToMillis(date: String): Long {
        try {
            return LocalDate.parse(date).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
        } catch (de: DateTimeParseException) {
            Log.d("ERROR", "DateTimeParseException occurred while parsing date: $date")
        }
        return 0
    }
}

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun checkIfDateIsEqualOrBeforeToday(date: LocalDate): Boolean {
    val today = LocalDate.now()
    return date.isBefore(today) || date.isEqual(today)
}