package com.jaideep.expensetracker.domain.usecase

import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.data.local.entities.Category
import com.jaideep.expensetracker.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
class GetAllCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke() = flow {
        emit(Resource.Loading())
        try {
            val categories = categoryRepository.getAllCategories()
            emit(Resource.Success(categories))
        }
        catch (ex: Exception) {
            emit(Resource.Error("Error while loading categories"))
        }
    }
}