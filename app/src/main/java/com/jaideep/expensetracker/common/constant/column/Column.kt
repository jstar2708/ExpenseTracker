package com.jaideep.expensetracker.common.constant.column

object Account {
    const val ALL = "accounts.*"
    const val ID = "accounts.id"
    const val NAME = "accounts.name"
    const val BALANCE = "accounts.balance"
    const val CREATED_ON = "accounts.createdOn"
}

object Transaction {
    const val ALL = "transactions.*"
    const val ID = "transactions.id"
    const val AMOUNT = "transactions.amount"
    const val ACCOUNT_ID = "transactions.accountId"
    const val CATEGORY_ID = "transactions.categoryId"
    const val CREATED_ON = "transactions.createdOn"
    const val IS_CREDIT = "transactions.isCredit"
    const val MESSAGE = "transactions.message"
}

object Category {
    const val ALL = "categories.*"
    const val ID = "categories.id"
    const val CATEGORY_NAME = "categories.name"
    const val ICON_NAME = "categories.iconName"
}