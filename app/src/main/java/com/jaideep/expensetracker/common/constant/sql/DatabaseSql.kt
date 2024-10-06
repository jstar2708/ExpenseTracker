package com.jaideep.expensetracker.common.constant.sql

object DatabaseSql {
    const val DELETE_PRIMARY_KEY_IDX = "DELETE FROM sqlite_sequence"
    const val DELETE_ALL_TRANSACTIONS = "DELETE from transactions"
    const val DELETE_ALL_ACCOUNTS = "DELETE from accounts"
    const val DELETE_ALL_CATEGORIES = "DELETE from categories"
}