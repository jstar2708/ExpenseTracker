package com.jaideep.expensetracker.presentation.utility

import com.jaideep.expensetracker.R

object Utility {
    fun getCategoryName(categoryId: Int): String {
        return when (categoryId) {
            R.drawable.food -> "Food"
            R.drawable.fuel -> "Fuel"
            R.drawable.travel -> "Travel"
            R.drawable.entertainment -> "Entertainment"
            R.drawable.shopping -> "Shopping"
            else -> ""
        }
    }
}