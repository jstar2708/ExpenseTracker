package com.jaideep.expensetracker.model

import com.jaideep.expensetracker.common.constant.TransactionMethod

open class TransactionMethodData(
    open val transactionMethod: TransactionMethod,
    open val accountName: String
)
