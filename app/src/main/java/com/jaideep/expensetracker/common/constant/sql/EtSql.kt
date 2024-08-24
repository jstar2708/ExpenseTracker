package com.jaideep.expensetracker.common.constant.sql

object EtSql {
    const val GET_ACCOUNT_BALANCE_BY_NAME = "Select Account.balance from Account where" +
            " accountName = :accountName"
    const val GET_ACCOUNT_BALANCE_FOR_ALL_ACCOUNTS = "Select sum(Account.balance) from Account"
    const val GET_AMOUNT_SPENT_FROM_ACCOUNT_TODAY = "Select sum(amount) from `Transaction`" +
            " inner join Account on Account.id = `Transaction`.accountId where " +
            " accountName = :accountName and isCredit = 0 and createdTime = :date"
    const val GET_AMOUNT_SPENT_FROM_ALL_ACCOUNT_TODAY = "Select sum(amount) from " +
            "`Transaction` where isCredit = 0 and createdTime = :date"
    const val GET_CATEGORY_ON_WHICH_MAX_AMOUNT_SPENT_FROM_ACCOUNT_THIS_MONTH = "With category_sums as (Select categoryName, iconName, sum(amount) as sum_amount" +
            " from `Transaction` inner join Category on `Transaction`.categoryId = Category.id" +
            " inner join Account on `Transaction`.accountId = Account.id" +
            " where accountName = :accountName and createdTime >= :date and isCredit = 0"+
            " group by categoryName, iconName)" +
            " Select categoryName, iconName, sum_amount as amountSpent" +
            " from category_sums where sum_amount = (select max(sum_amount) from category_sums)"
    const val GET_AMOUNT_SPENT_FROM_ACCOUNT_THIS_MONTH = "Select sum(amount) from `Transaction`" +
            " inner join Account on `Transaction`.accountId = Account.id" +
            " where createdTime >= :date and isCredit = 0 and accountName = :accountName"
    const val GET_AMOUNT_SPENT_FROM_ALL_ACCOUNTS_THIS_MONTH = "Select sum(amount) from `Transaction`" +
            " where createdTime >= :date and isCredit = 0"
    const val GET_CATEGORY_ON_WHICH_MAX_AMOUNT_SPENT_FROM_ALL_ACCOUNTS_THIS_MONTH = "With category_sums as (Select categoryName, iconName, sum(amount) as sum_amount" +
            " from `Transaction` inner join Category on `Transaction`.categoryId = Category.id" +
            " where createdTime >= :date and isCredit = 0"+
            " group by categoryName, iconName)" +
            " Select categoryName, iconName, sum_amount as amountSpent" +
            " from category_sums where sum_amount = (select max(sum_amount) from category_sums)"
}