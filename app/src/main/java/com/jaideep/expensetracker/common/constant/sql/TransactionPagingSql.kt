package com.jaideep.expensetracker.common.constant.sql

import com.jaideep.expensetracker.common.constant.column.Transaction

object TransactionPagingSql {
    // Paging
    const val GET_TRANSACTION_BY_ID = "SELECT ${TransactionSql.TRANSACTION_COLUMNS} WHERE ${Transaction.ID} = :transactionId"
    const val GET_ALL_TRANSACTIONS = "SELECT ${TransactionSql.TRANSACTION_COLUMNS} ORDER BY ${Transaction.CREATED_ON} DESC"
    const val GET_ALL_DEBIT_TRANSACTIONS =
        "SELECT ${TransactionSql.TRANSACTION_COLUMNS} WHERE ${Transaction.IS_CREDIT} = 0  ORDER BY ${Transaction.CREATED_ON} DESC"
    const val GET_ALL_CREDIT_TRANSACTIONS =
        "SELECT ${TransactionSql.TRANSACTION_COLUMNS} WHERE ${Transaction.IS_CREDIT} = 1  ORDER BY ${Transaction.CREATED_ON} DESC"
    const val GET_ACCOUNT_TRANSACTIONS =
        "SELECT ${TransactionSql.TRANSACTION_COLUMNS} WHERE ${Transaction.ACCOUNT_ID} = :accountId  ORDER BY ${Transaction.CREATED_ON} DESC"
    const val GET_ACCOUNT_DEBIT_TRANSACTIONS =
        "SELECT ${TransactionSql.TRANSACTION_COLUMNS} WHERE ${Transaction.ACCOUNT_ID} = :accountId AND ${Transaction.IS_CREDIT} = 0  ORDER BY ${Transaction.CREATED_ON} DESC"
    const val GET_ACCOUNT_CREDIT_TRANSACTIONS =
        "SELECT ${TransactionSql.TRANSACTION_COLUMNS} WHERE ${Transaction.ACCOUNT_ID} = :accountId AND ${Transaction.IS_CREDIT} = 1 ORDER BY ${Transaction.CREATED_ON} DESC"
    const val GET_ACCOUNT_TRANSACTIONS_BETWEEN_DATES =
        "SELECT ${TransactionSql.TRANSACTION_COLUMNS} WHERE ${Transaction.ACCOUNT_ID} = :accountId AND ${Transaction.CREATED_ON} >= :startDate AND ${Transaction.CREATED_ON} <= :endDate ORDER BY ${Transaction.CREATED_ON} DESC"
    const val GET_ACCOUNT_DEBIT_TRANSACTIONS_BETWEEN_DATES =
        "SELECT ${TransactionSql.TRANSACTION_COLUMNS} WHERE ${Transaction.ACCOUNT_ID} = :accountId AND ${Transaction.IS_CREDIT} = 0 AND ${Transaction.CREATED_ON} >= :startDate AND ${Transaction.CREATED_ON} <= :endDate ORDER BY ${Transaction.CREATED_ON} DESC"
    const val GET_ACCOUNT_CREDIT_TRANSACTIONS_BETWEEN_DATES =
        "SELECT ${TransactionSql.TRANSACTION_COLUMNS} WHERE ${Transaction.ACCOUNT_ID} = :accountId AND ${Transaction.IS_CREDIT} = 1 AND ${Transaction.CREATED_ON} >= :startDate AND ${Transaction.CREATED_ON} <= :endDate ORDER BY ${Transaction.CREATED_ON} DESC"
    const val GET_TRANSACTIONS_BETWEEN_DATES =
        "SELECT ${TransactionSql.TRANSACTION_COLUMNS} WHERE ${Transaction.CREATED_ON} >= :startDate AND ${Transaction.CREATED_ON} <= :endDate ORDER BY ${Transaction.CREATED_ON} DESC"
    const val GET_DEBIT_TRANSACTIONS_BETWEEN_DATES =
        "SELECT ${TransactionSql.TRANSACTION_COLUMNS} WHERE ${Transaction.IS_CREDIT} = 0 AND ${Transaction.CREATED_ON} >= :startDate AND ${Transaction.CREATED_ON} <= :endDate ORDER BY ${Transaction.CREATED_ON} DESC"
    const val GET_CREDIT_TRANSACTIONS_BETWEEN_DATES = "SELECT ${TransactionSql.TRANSACTION_COLUMNS} WHERE ${Transaction.IS_CREDIT} = 1 AND ${Transaction.CREATED_ON} >= :startDate AND ${Transaction.CREATED_ON} <= :endDate ORDER BY ${Transaction.CREATED_ON} DESC"
}