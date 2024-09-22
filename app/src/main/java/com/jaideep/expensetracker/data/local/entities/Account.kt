package com.jaideep.expensetracker.data.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate


@Entity(tableName = "accounts", indices = [Index(value = ["name"], unique = true)])
data class Account(
    @PrimaryKey(autoGenerate = true) var id: Int,
    val name: String,
    val balance: Double,
    val createdOn: LocalDate
)