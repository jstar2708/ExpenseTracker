package com.jaideep.expensetracker.domain.usecase

import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.domain.repository.CategoryRepository
import com.jaideep.expensetracker.model.dto.CategoryDto
import com.jaideep.expensetracker.presentation.utility.Utility.getCategoryIconId
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.transform
import javax.inject.Inject


class GetAllCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke() = categoryRepository.getAllCategories().map {
        it.asFlow().map { category ->
            CategoryDto(
                category.id, category.name, getCategoryIconId(category.iconName)
            )
        }.toList()
    }.transform<List<CategoryDto>, Resource<List<CategoryDto>>> {
        emit(Resource.Success(it))
    }.onStart {
            emit(Resource.Loading())
    }.catch {
            emit(Resource.Error("Error while fetching the categories"))
        }
}