package com.jaideep.expensetracker.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(indices = [Index(value = ["accountName"], unique = true)])
data class Account(
    @PrimaryKey(autoGenerate = true) var id: Int,
    val accountName: String,
    val balance: Double,
    val createdOn: Long
)