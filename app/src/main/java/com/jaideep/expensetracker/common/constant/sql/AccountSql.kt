package com.jaideep.expensetracker.common.constant.sql

import com.jaideep.expensetracker.common.constant.sql.table.Account

object AccountSql {
    const val GET_ALL_ACCOUNTS: String = "Select ${Account.ALL} from accounts"
    const val GET_ACCOUNT_BY_ID: String = "Select ${Account.ALL} from accounts where ${Account.ID} = :accountId"
    const val UPDATE_ACCOUNT_BALANCE: String = "Update accounts set balance = ${Account.BALANCE} + :amount where ${Account.ID} = :accountId"
    const val GET_ACCOUNTS_COUNT: String = "Select IFNULL(Count(${Account.ID}), 0) from accounts"
}