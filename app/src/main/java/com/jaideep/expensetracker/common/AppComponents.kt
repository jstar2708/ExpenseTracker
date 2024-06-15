package com.jaideep.expensetracker.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.CurrencyRupee
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object AppComponents {

    val list = listOf<BottomNavigationItem>(
        BottomNavigationItem(
            "Home",
            Icons.Filled.Home,
            Icons.Outlined.Home,
            MainScreen.HOME
        ),
        BottomNavigationItem(
            "Transactions",
            Icons.Filled.CurrencyRupee,
            Icons.Outlined.CurrencyRupee,
            MainScreen.TRANSACTIONS
        ),
        BottomNavigationItem(
            "Category",
            Icons.Filled.Category,
            Icons.Outlined.Category,
            MainScreen.CATEGORY
        ),
        BottomNavigationItem(
            "Settings",
            Icons.Filled.Settings,
            Icons.Outlined.Settings,
            MainScreen.SETTINGS
        )
    )

    fun Long.convertMilliSecondsToDate() : String {
        val date = Date(this)
        val format = SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH)
        return format.format(date)
    }

    fun Long.convertMilliSecondsToDateTime() : String {
        val date = Date(this)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.ENGLISH)
        return format.format(date)
    }

    fun String.convertDateToMilliseconds() : Long {
        val format = SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH)
        val mDate: Date? = format.parse(this)
        return mDate?.time ?: 0
    }

    fun String.convertDateTimeToMilliseconds() : Long {
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.ENGLISH)
        val mDate: Date? = format.parse(this)
        return mDate?.time ?: 0
    }


}

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)