package com.jaideep.expensetracker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val categoryName: String,
    val iconName: String
)