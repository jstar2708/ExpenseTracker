package com.jaideep.expensetracker.model

import com.jaideep.expensetracker.common.constant.TransactionMethod

class TransactionMethodDataForDates(
    override val transactionMethod: TransactionMethod,
    override val accountName: String,
    val startDate: Long,
    val endDate: Long,
) : TransactionMethodData(transactionMethod, accountName)