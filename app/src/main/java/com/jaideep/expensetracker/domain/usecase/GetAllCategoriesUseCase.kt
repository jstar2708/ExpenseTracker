package com.jaideep.expensetracker.domain.usecase

import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetAllCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke() = flow {
        emit(Resource.Loading())
        categoryRepository.getAllCategories().collect {
            emit(Resource.Success(it))
        }
    }.catch {
        emit(Resource.Error("Error while fetching the categories"))
    }
}