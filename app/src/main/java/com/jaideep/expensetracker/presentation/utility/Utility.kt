package com.jaideep.expensetracker.presentation.utility

import android.util.Log
import androidx.compose.animation.core.rememberTransition
import com.jaideep.expensetracker.R
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeParseException

object Utility {
    fun getCategoryIconName(categoryId: Int): String {
        return when (categoryId) {
            R.drawable.food -> "Food"
            R.drawable.fuel -> "Fuel"
            R.drawable.travel -> "Travel"
            R.drawable.entertainment -> "Entertainment"
            R.drawable.shopping -> "Shopping"
            else -> "Other"
        }
    }

    fun getCategoryIconId(iconName: String): Int {
        return when (iconName) {
            "Food" -> R.drawable.food
            "Fuel" -> R.drawable.fuel
            "Entertainment" -> R.drawable.entertainment
            "Shopping" -> R.drawable.shopping
            "Travel" -> R.drawable.travel
            else -> R.drawable.category
        }
    }

    fun getCurrentDateInMillis(): Long {
        return LocalDate.now().atStartOfDay(ZoneId.of("Asia/Kolkata")).toEpochSecond() * 1000
    }

    fun getStartDateOfMonthInMillis(): Long {
        return LocalDate.now().withDayOfMonth(1).toEpochDay() * 86_400_000L
    }

    fun getStartDateOfYearInMillis(): Long {
        return LocalDate.now().withDayOfYear(1).toEpochDay() * 86_400_000L
    }

    fun stringDateToMillis(date: String): Long {
        try {
            return LocalDate.parse(date).atStartOfDay(ZoneId.of("Asia/Kolkata"))
                .toEpochSecond() * 1000
        }
        catch (de: DateTimeParseException) {
            Log.d("ERROR", "DateTimeParseException occurred while parsing date: $date")
        }
        return 0
    }


}