package com.jaideep.expensetracker.common.constant.sql

object TransactionSql {
    const val GET_TRANSACTION_BY_ID = "SELECT * FROM `Transaction` WHERE id = :transactionId"
    const val GET_ALL_TRANSACTIONS =
        "SELECT * FROM `Transaction` ORDER BY createdTime DESC LIMIT :limit"
    const val GET_ALL_DEBIT_TRANSACTIONS =
        "SELECT * FROM `Transaction` WHERE isCredit = 0  ORDER BY createdTime DESC LIMIT :limit"
    const val GET_ALL_CREDIT_TRANSACTIONS =
        "SELECT * FROM `Transaction` WHERE isCredit = 1  ORDER BY createdTime DESC LIMIT :limit"
    const val GET_ACCOUNT_TRANSACTIONS =
        "SELECT * FROM `Transaction` WHERE accountId = :accountId  ORDER BY createdTime DESC LIMIT :limit"
    const val GET_ACCOUNT_DEBIT_TRANSACTIONS =
        "SELECT * FROM `Transaction`WHERE accountId = :accountId AND isCredit = 0  ORDER BY createdTime DESC LIMIT :limit"
    const val GET_ACCOUNT_CREDIT_TRANSACTIONS =
        "SELECT * FROM `Transaction` WHERE accountId = :accountId AND isCredit = 1 ORDER BY createdTime DESC LIMIT :limit"
    const val GET_ACCOUNT_TRANSACTIONS_BETWEEN_DATES =
        "SELECT * FROM `Transaction` WHERE accountId = :accountId AND createdTime >= :startDate AND createdTime <= :endDate ORDER BY createdTime DESC LIMIT :limit"
    const val GET_ACCOUNT_DEBIT_TRANSACTIONS_BETWEEN_DATES =
        "SELECT * FROM `Transaction` WHERE accountId = :accountId AND isCredit = 0 AND createdTime >= :startDate AND createdTime <= :endDate ORDER BY createdTime DESC LIMIT :limit"
    const val GET_ACCOUNT_CREDIT_TRANSACTIONS_BETWEEN_DATES =
        "SELECT * FROM `Transaction` WHERE accountId = :accountId AND isCredit = 1 AND createdTime >= :startDate AND createdTime <= :endDate ORDER BY createdTime DESC LIMIT :limit"
    const val GET_TRANSACTIONS_BETWEEN_DATES =
        "SELECT * FROM `Transaction` WHERE createdTime >= :startDate AND createdTime <= :endDate ORDER BY createdTime DESC LIMIT :limit"
    const val GET_DEBIT_TRANSACTIONS_BETWEEN_DATES =
        "SELECT * FROM `Transaction` WHERE isCredit = 0 AND createdTime >= :startDate AND createdTime <= :endDate ORDER BY createdTime DESC LIMIT :limit"
    const val GET_CREDIT_TRANSACTIONS_BETWEEN_DATES =
        "SELECT * FROM `Transaction` WHERE isCredit = 1 AND createdTime >= :startDate AND createdTime <= :endDate ORDER BY createdTime DESC LIMIT :limit"
}