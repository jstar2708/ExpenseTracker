package com.jaideep.expensetracker.common.constant.sql

import com.jaideep.expensetracker.common.constant.column.Account
import com.jaideep.expensetracker.common.constant.column.Category
import com.jaideep.expensetracker.common.constant.column.Transaction

object TransactionSql {
    const val TRANSACTION_COLUMNS =
        "${Transaction.ID} as transactionId, ${Transaction.MESSAGE}, ${Transaction.AMOUNT}," + " ${Account.NAME} as accountName, ${Transaction.CREATED_ON}, ${Transaction.IS_CREDIT}, ${Category.ID} as categoryId, ${Category.CATEGORY_NAME} as categoryName, ${Category.ICON_NAME} as iconName" + " FROM transactions inner join categories on ${Transaction.ID} = ${Category.ID} inner join accounts on " + "${Transaction.ACCOUNT_ID} = ${Account.ID}"
    const val DELETE_TRANSACTION_BY_ID =
        "DELETE FROM transactions where ${Transaction.ID} = :transactionId"
    const val GET_TRANSACTION_BY_ID =
        "SELECT $TRANSACTION_COLUMNS WHERE ${Transaction.ID} = :transactionId"
    const val GET_ALL_TRANSACTIONS =
        "SELECT $TRANSACTION_COLUMNS ORDER BY ${Transaction.CREATED_ON} DESC LIMIT :limit"
    const val GET_ALL_DEBIT_TRANSACTIONS =
        "SELECT $TRANSACTION_COLUMNS WHERE ${Transaction.IS_CREDIT} = 0  ORDER BY ${Transaction.CREATED_ON} DESC LIMIT :limit"
    const val GET_ALL_CREDIT_TRANSACTIONS =
        "SELECT $TRANSACTION_COLUMNS WHERE ${Transaction.IS_CREDIT} = 1  ORDER BY ${Transaction.CREATED_ON} DESC LIMIT :limit"
    const val GET_ACCOUNT_TRANSACTIONS =
        "SELECT $TRANSACTION_COLUMNS WHERE ${Account.NAME} = :accountName  ORDER BY ${Transaction.CREATED_ON} DESC LIMIT :limit"
    const val GET_ACCOUNT_DEBIT_TRANSACTIONS =
        "SELECT $TRANSACTION_COLUMNS WHERE ${Account.NAME} = :accountName AND ${Transaction.IS_CREDIT} = 0  ORDER BY ${Transaction.CREATED_ON} DESC LIMIT :limit"
    const val GET_ACCOUNT_CREDIT_TRANSACTIONS =
        "SELECT $TRANSACTION_COLUMNS WHERE ${Account.NAME} = :accountName AND ${Transaction.IS_CREDIT} = 1 ORDER BY ${Transaction.CREATED_ON} DESC LIMIT :limit"
    const val GET_ACCOUNT_TRANSACTIONS_BETWEEN_DATES =
        "SELECT $TRANSACTION_COLUMNS WHERE ${Account.NAME} = :accountName AND ${Transaction.CREATED_ON} >= :startDate AND ${Transaction.CREATED_ON} <= :endDate ORDER BY ${Transaction.CREATED_ON} DESC LIMIT :limit"
    const val GET_ACCOUNT_DEBIT_TRANSACTIONS_BETWEEN_DATES =
        "SELECT $TRANSACTION_COLUMNS WHERE ${Account.NAME} = :accountName AND ${Transaction.IS_CREDIT} = 0 AND ${Transaction.CREATED_ON} >= :startDate AND ${Transaction.CREATED_ON} <= :endDate ORDER BY ${Transaction.CREATED_ON} DESC LIMIT :limit"
    const val GET_ACCOUNT_CREDIT_TRANSACTIONS_BETWEEN_DATES =
        "SELECT $TRANSACTION_COLUMNS WHERE ${Account.NAME} = :accountName AND ${Transaction.IS_CREDIT} = 1 AND ${Transaction.CREATED_ON} >= :startDate AND ${Transaction.CREATED_ON} <= :endDate ORDER BY ${Transaction.CREATED_ON} DESC LIMIT :limit"
    const val GET_TRANSACTIONS_BETWEEN_DATES =
        "SELECT $TRANSACTION_COLUMNS WHERE ${Transaction.CREATED_ON} >= :startDate AND ${Transaction.CREATED_ON} <= :endDate ORDER BY ${Transaction.CREATED_ON} DESC LIMIT :limit"
    const val GET_DEBIT_TRANSACTIONS_BETWEEN_DATES =
        "SELECT $TRANSACTION_COLUMNS WHERE ${Transaction.IS_CREDIT} = 0 AND ${Transaction.CREATED_ON} >= :startDate AND ${Transaction.CREATED_ON} <= :endDate ORDER BY ${Transaction.CREATED_ON} DESC LIMIT :limit"
    const val GET_CREDIT_TRANSACTIONS_BETWEEN_DATES =
        "SELECT $TRANSACTION_COLUMNS WHERE ${Transaction.IS_CREDIT} = 1 AND ${Transaction.CREATED_ON} >= :startDate AND ${Transaction.CREATED_ON} <= :endDate ORDER BY ${Transaction.CREATED_ON} DESC LIMIT :limit"
}