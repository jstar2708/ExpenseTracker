package com.jaideep.expensetracker.domain.repository

import com.jaideep.expensetracker.data.local.entities.Category

interface CategoryRepository {
    suspend fun getCategoryById(categoryId: Int) : Category
    suspend fun saveCategory(category: Category)
    suspend fun getAllCategories() : List<Category>
}