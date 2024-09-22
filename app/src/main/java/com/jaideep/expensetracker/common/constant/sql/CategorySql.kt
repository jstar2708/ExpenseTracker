package com.jaideep.expensetracker.common.constant.sql

import com.jaideep.expensetracker.common.constant.column.Category

object CategorySql {
    const val GET_CATEGORY_BY_ID =
        "SELECT ${Category.ALL} from categories where ${Category.ID} = :categoryId"
    const val GET_ALL_CATEGORIES = "SELECT ${Category.ALL} from categories"
    const val GET_ALL_CATEGORIES_COUNT = "SELECT Count(${Category.ICON_NAME}) from categories"
    const val GET_CATEGORY_BY_NAME =
        "SELECT ${Category.ALL} from categories where ${Category.CATEGORY_NAME} = :categoryName"
}