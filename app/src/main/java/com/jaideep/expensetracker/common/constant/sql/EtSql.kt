package com.jaideep.expensetracker.common.constant.sql

import com.jaideep.expensetracker.common.constant.sql.table.Account
import com.jaideep.expensetracker.common.constant.sql.table.Category
import com.jaideep.expensetracker.common.constant.sql.table.Transaction

object EtSql {
    const val GET_ACCOUNT_BALANCE_BY_NAME =
        "Select ${Account.BALANCE} from accounts where" + " ${Account.NAME} = :accountName"

    const val GET_ACCOUNT_BALANCE_FOR_ALL_ACCOUNTS = "Select sum(${Account.BALANCE}) from accounts"

    const val GET_AMOUNT_SPENT_FROM_ACCOUNT_TODAY =
        "Select IFNULL(sum(${Transaction.AMOUNT}), 0.0) from transactions" + " inner join accounts on ${Account.ID} = ${Transaction.ACCOUNT_ID} where " + " ${Account.NAME} = :accountName and ${Transaction.IS_CREDIT} = 0 and ${Transaction.CREATED_ON} = :date"

    const val GET_AMOUNT_SPENT_FROM_ALL_ACCOUNT_TODAY =
        "Select IFNULL(sum(${Transaction.AMOUNT}), 0) from " + "transactions where ${Transaction.IS_CREDIT} = 0 and ${Transaction.CREATED_ON} = :date"

    const val GET_CATEGORY_ON_WHICH_MAX_AMOUNT_SPENT_FROM_ACCOUNT_THIS_MONTH =
        "With category_sums as (Select ${Category.CATEGORY_NAME} as categoryName, ${Category.ICON_NAME} as iconName, sum(${Transaction.AMOUNT}) as sum_amount" + " from transactions inner join categories on ${Transaction.CATEGORY_ID} = ${Category.ID}" + " inner join accounts on ${Transaction.ACCOUNT_ID} = ${Account.ID}" + " where ${Account.NAME} = :accountName and ${Transaction.CREATED_ON} >= :date and ${Transaction.IS_CREDIT} = 0" + " group by ${Category.CATEGORY_NAME}, ${Category.ICON_NAME})" + " Select categoryName, iconName, sum_amount as amountSpent" + " from category_sums where sum_amount = (select max(sum_amount) from category_sums)"

    const val GET_AMOUNT_SPENT_FROM_ACCOUNT_THIS_MONTH =
        "Select IFNULL(sum(${Transaction.AMOUNT}), 0) from transactions" + " inner join accounts on ${Transaction.ACCOUNT_ID} = ${Account.ID}" + " where ${Transaction.CREATED_ON} >= :date and ${Transaction.IS_CREDIT} = 0 and ${Account.NAME} = :accountName"

    const val GET_AMOUNT_SPENT_FROM_ALL_ACCOUNTS_THIS_MONTH =
        "Select IFNULL(sum(${Transaction.AMOUNT}), 0) from transactions" + " where ${Transaction.CREATED_ON} >= :date and ${Transaction.IS_CREDIT} = 0"

    const val GET_CATEGORY_ON_WHICH_MAX_AMOUNT_SPENT_FROM_ALL_ACCOUNTS_THIS_MONTH =
        "With category_sums as (Select ${Category.CATEGORY_NAME} as categoryName, ${Category.ICON_NAME} as iconName, IFNULL(sum(${Transaction.AMOUNT}), 0) as sum_amount" + " from categories left join transactions on ${Transaction.CATEGORY_ID} = ${Category.ID}" + " where (${Transaction.CREATED_ON} IS NULL or ${Transaction.CREATED_ON} >= :date) and (${Transaction.IS_CREDIT} IS NULL or ${Transaction.IS_CREDIT} = 0)" + " group by ${Category.CATEGORY_NAME}, ${Category.ICON_NAME})" + " Select categoryName, iconName, IFNULL(sum_amount, 0) as amountSpent" + " from category_sums where sum_amount = (select IFNULL(max(sum_amount), 0) from category_sums) order by amountSpent limit 1"

    const val GET_CATEGORY_CARDS_DATA_FROM_ALL_ACCOUNTS_WITHIN_DURATION =
        "With category_sums as (Select ${Category.CATEGORY_NAME} as categoryName, ${Category.ICON_NAME} as iconName, IFNULL(sum(${Transaction.AMOUNT}), 0) as sum_amount" + " from categories left join transactions on ${Transaction.CATEGORY_ID} = ${Category.ID}" + " where (${Transaction.CREATED_ON} IS NULL or ${Transaction.CREATED_ON} >= :date) and (${Transaction.IS_CREDIT} IS NULL or ${Transaction.IS_CREDIT} = 0)" + " group by ${Category.CATEGORY_NAME}, ${Category.ICON_NAME})" + " Select categoryName, iconName, sum_amount as amountSpent" + " from category_sums"

    const val GET_CATEGORY_CARDS_DATA_FROM_ACCOUNT_WITHIN_DURATION =
        "With category_sums as (Select ${Category.CATEGORY_NAME} as categoryName, ${Category.ICON_NAME} as iconName, IFNULL(sum(${Transaction.AMOUNT}), 0) as sum_amount" + " from categories left join transactions on ${Transaction.CATEGORY_ID} = ${Category.ID}" + " left join accounts on ${Transaction.ACCOUNT_ID} = ${Account.ID}" + " where (${Transaction.CREATED_ON} IS NULL or ${Transaction.CREATED_ON} >= :date) and (${Transaction.IS_CREDIT} IS NULL or ${Transaction.IS_CREDIT} = 0)" + " and (${Account.NAME} IS NULL or ${Account.NAME} = :accountName)" + " group by ${Category.CATEGORY_NAME}, ${Category.ICON_NAME})" + " Select categoryName, iconName, sum_amount as amountSpent" + " from category_sums"

    const val GET_ALL_CATEGORY_WISE_TRANSACTIONS_WITH_DATE =
        "Select ${TransactionSql.TRANSACTION_COLUMNS} where ${Category.CATEGORY_NAME}" + " = :categoryName and ${Transaction.CREATED_ON} >= :fromDate and ${Transaction.CREATED_ON} <= :toDate"

    const val GET_CATEGORY_WISE_ACCOUNT_TRANSACTIONS_WITH_DATE =
        "Select ${TransactionSql.TRANSACTION_COLUMNS} where ${Category.CATEGORY_NAME}" + " = :categoryName and ${Account.NAME} = :accountName and ${Transaction.CREATED_ON} >= :fromDate and ${Transaction.CREATED_ON} <= :toDate"

    const val GET_TOTAL_EXPENDITURE = "Select IFNULL(sum(${Transaction.AMOUNT}), 0) from transactions where ${Transaction.IS_CREDIT} = 0"

    const val GET_TOTAL_TRANSACTIONS_COUNT = "Select IFNULL(count(${Transaction.ID}), 0) from transactions"

    const val GET_LAST_TRANSACTION_DATE = "Select IFNULL(${Transaction.CREATED_ON}, 0) from transactions ORDER BY ${Transaction.CREATED_ON} DESC limit 1"

    const val GET_MOST_FREQUENTLY_USED_ACC = "With tempView as (Select ${Transaction.ACCOUNT_ID} as accountId, count(${Transaction.ACCOUNT_ID}) as accountCount from transactions group by accountId) Select ${Account.NAME} from accounts left join tempView on ${Account.ID} = accountId where accountCount = (Select MAX(accountCount) from tempView) limit 1"

    const val GET_FIRST_TRANSACTION_DATE = "Select IFNULL(${Transaction.CREATED_ON}, 0) from transactions ORDER BY ${Transaction.CREATED_ON} limit 1"
}