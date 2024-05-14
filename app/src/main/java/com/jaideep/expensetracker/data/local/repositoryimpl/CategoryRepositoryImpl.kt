package com.jaideep.expensetracker.data.local.repositoryimpl

import com.jaideep.expensetracker.data.local.dao.CategoryDao
import com.jaideep.expensetracker.data.local.entities.Category
import com.jaideep.expensetracker.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val dao: CategoryDao
): CategoryRepository {
    override suspend fun getCategoryById(categoryId: Int): Category {
        return dao.getCategoryById(categoryId)
    }

    override suspend fun saveCategory(category: Category) {
        dao.saveCategory(category)
    }

    override suspend fun getAllCategories(): List<Category> {
        return dao.getAllCategories()
    }

}