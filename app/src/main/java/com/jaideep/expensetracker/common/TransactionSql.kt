package com.jaideep.expensetracker.common

object TransactionSql {
    const val GET_TRANSACTION_BY_ID = "Select * from `Transaction` where id = :transactionId"
    const val GET_ALL_TRANSACTIONS = "Select * from `Transaction`"
    const val GET_ALL_DEBIT_TRANSACTIONS = "Select * from `Transaction` where isCredit = 0"
    const val GET_ALL_CREDIT_TRANSACTIONS = "Select * from `Transaction` where isCredit = 1"
    const val GET_ACCOUNT_TRANSACTIONS = "Select * from `Transaction` where accountId = :accountId"
    const val GET_ACCOUNT_DEBIT_TRANSACTIONS = "Select * from `Transaction`where accountId = :accountId and isCredit = 0"
    const val GET_ACCOUNT_CREDIT_TRANSACTIONS = "Select * from `Transaction` where accountId = :accountId and isCredit = 1"
    const val GET_ACCOUNT_TRANSACTIONS_BETWEEN_DATES = "Select * from `Transaction` where accountId = :accountId and createdTime >= :startDate and createdTime <= :endDate"
    const val GET_ACCOUNT_DEBIT_TRANSACTIONS_BETWEEN_DATES = "Select * from `Transaction` where accountId = :accountId and isCredit = 0 and createdTime >= :startDate and createdTime <= :endDate"
    const val GET_ACCOUNT_CREDIT_TRANSACTIONS_BETWEEN_DATES = "Select * from `Transaction` where accountId = :accountId and isCredit = 1 and createdTime >= :startDate and createdTime <= :endDate"
    const val GET_TRANSACTIONS_BETWEEN_DATES = "Select * from `Transaction` createdTime >= :startDate and createdTime <= :endDate"
    const val GET_DEBIT_TRANSACTIONS_BETWEEN_DATES = "Select * from `Transaction` where isCredit = 0 and createdTime >= :startDate and createdTime <= :endDate"
    const val GET_CREDIT_TRANSACTIONS_BETWEEN_DATES = "Select * from `Transaction` where isCredit = 1 and createdTime >= :startDate and createdTime <= :endDate"
}