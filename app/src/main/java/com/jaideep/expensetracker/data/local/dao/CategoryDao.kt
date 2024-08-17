package com.jaideep.expensetracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jaideep.expensetracker.common.constant.sql.CategorySql.GET_ALL_CATEGORIES
import com.jaideep.expensetracker.common.constant.sql.CategorySql.GET_ALL_CATEGORIES_COUNT
import com.jaideep.expensetracker.common.constant.sql.CategorySql.GET_CATEGORY_BY_ID
import com.jaideep.expensetracker.data.local.entities.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query(GET_CATEGORY_BY_ID)
    suspend fun getCategoryById(categoryId: Int): Category

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCategory(category: Category)

    @Query(GET_ALL_CATEGORIES)
    fun getAllCategories(): Flow<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllCategories(categories: List<Category>)

    @Query(GET_ALL_CATEGORIES_COUNT)
    suspend fun getAllCategoriesCount(): Int
}