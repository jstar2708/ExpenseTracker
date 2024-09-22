package com.jaideep.expensetracker.data.local.repositoryimpl

import com.jaideep.expensetracker.data.local.dao.CategoryDao
import com.jaideep.expensetracker.data.local.entities.Category
import com.jaideep.expensetracker.domain.repository.CategoryRepository
import com.jaideep.expensetracker.model.dto.CategoryDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val dao: CategoryDao
) : CategoryRepository {
    override suspend fun getCategoryById(categoryId: Int): Category {
        return dao.getCategoryById(categoryId)
    }

    override suspend fun saveCategory(category: Category) {
        dao.saveCategory(category)
    }

    override fun getAllCategories(): Flow<List<Category>> {
        return dao.getAllCategories()
    }

    override suspend fun saveAllCategories(categories: List<Category>) {
        return dao.saveAllCategories(categories)
    }

    override suspend fun getAllCategoriesCount(): Int {
        return dao.getAllCategoriesCount()
    }

    override suspend fun getCategoryByName(categoryName: String): Category {
        return dao.getCategoryByName(categoryName)
    }

}