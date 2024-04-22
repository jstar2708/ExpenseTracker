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
            Routes.HOME
        ),
        BottomNavigationItem(
            "Transactions",
            Icons.Filled.CurrencyRupee,
            Icons.Outlined.CurrencyRupee,
            Routes.TRANSACTIONS
        ),
        BottomNavigationItem(
            "Category",
            Icons.Filled.Category,
            Icons.Outlined.Category,
            Routes.CATEGORY
        ),
        BottomNavigationItem(
            "Settings",
            Icons.Filled.Settings,
            Icons.Outlined.Settings,
            Routes.SETTINGS
        )
    )

    fun Long.convertMilliSecondsToDate() : String {
        val date = Date(this)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.ENGLISH)
        return format.format(date)
    }

    fun String.convertDateToMilliseconds() : Long {
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