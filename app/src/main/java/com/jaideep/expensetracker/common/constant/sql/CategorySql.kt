package com.jaideep.expensetracker.common.constant.sql

object CategorySql {
    const val GET_CATEGORY_BY_ID = "Select * from category where id = :categoryId"
    const val GET_ALL_CATEGORIES = "Select * from Category"
}