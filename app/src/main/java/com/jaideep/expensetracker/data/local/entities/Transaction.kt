package com.jaideep.expensetracker.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(foreignKeys = [
    ForeignKey(
        entity = Account::class,
        parentColumns = ["id", ],
        childColumns = ["accountId"],
        onDelete = CASCADE,
        onUpdate = CASCADE
    ),
    ForeignKey(
        entity = Category::class,
        parentColumns = ["id", ],
        childColumns = ["categoryId"],
        onDelete = CASCADE,
        onUpdate = CASCADE
    )
]
    )
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val amount: Double,
    val accountId: Int,
    val categoryId: Int,
    val message: String,
    val createdTime: Long,
    val isCredit: Int
)
