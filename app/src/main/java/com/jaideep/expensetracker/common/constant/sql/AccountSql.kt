package com.jaideep.expensetracker.common.constant.sql

object AccountSql {
    const val GET_ALL_ACCOUNTS: String = "Select * from Account"
    const val GET_ACCOUNT_BY_ID: String = "Select * from account where id = :accountId"
}