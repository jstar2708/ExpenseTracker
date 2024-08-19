package com.jaideep.expensetracker.common.constant.sql

object EtSql {
    const val GET_ACCOUNT_BALANCE_BY_NAME = "Select Account.balance from Account where accountName = :accountName"
    const val GET_ACCOUNT_BALANCE_FOR_ALL_ACCOUNTS = "Select sum(Account.balance) from Account"
    const val GET_AMOUNT_SPENT_FROM_ACCOUNT = "Select sum(amount) from `Transaction` inner join Account on Account.id = `Transaction`.accountId where accountName = :accountName and isCredit = 0 and createdTime >= :date"
    const val GET_AMOUNT_SPENT_FROM_ALL_ACCOUNT = "Select sum(amount) from `Transaction` where isCredit = 0 and createdTime >= :date"
}