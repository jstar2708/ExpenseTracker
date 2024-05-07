package com.jaideep.expensetracker.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jaideep.expensetracker.common.CategorySql.GET_CATEGORY_BY_ID
import com.jaideep.expensetracker.data.entities.Category

@Dao
interface CategoryDao {
    @Query(GET_CATEGORY_BY_ID)
    suspend fun getCategoryById(categoryId: Int) : Category
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCategory(category: Category)
    @Query("Select * from Category")
    suspend fun getAllCategories() : List<Category>
}