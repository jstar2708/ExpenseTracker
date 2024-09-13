package com.jaideep.expensetracker.domain.usecase

import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCategoryByNameUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(categoryName: String) = flow {
        emit(Resource.Loading())
        emit(Resource.Success(categoryRepository.getCategoryByName(categoryName)))
    }.catch {
        emit(Resource.Error("Error while fetching category"))
    }
}
