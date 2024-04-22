package com.jaideep.expensetracker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Account(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val accountName: String,
    val balance: Double,
    val createdOn: Long
)