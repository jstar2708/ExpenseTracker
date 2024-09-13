package com.jaideep.expensetracker.domain.repository

import com.jaideep.expensetracker.data.local.entities.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getCategoryById(categoryId: Int): Category
    suspend fun saveCategory(category: Category)
    fun getAllCategories(): Flow<List<Category>>
    suspend fun saveAllCategories(categories: List<Category>)
    suspend fun getAllCategoriesCount(): Int
    suspend fun getCategoryByName(categoryName: String): Category
}