package com.jaideep.expensetracker.domain.usecase

import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.domain.repository.CategoryRepository
import com.jaideep.expensetracker.model.dto.CategoryDto
import com.jaideep.expensetracker.presentation.utility.Utility.getCategoryIconId
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCategoryByNameUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(categoryName: String) = flow {
        emit(Resource.Loading())
        val category = categoryRepository.getCategoryByName(categoryName)
        emit(Resource.Success(CategoryDto(category.categoryName, getCategoryIconId(category.iconName))))
    }.catch {
        emit(Resource.Error("Error while fetching category"))
    }
}
