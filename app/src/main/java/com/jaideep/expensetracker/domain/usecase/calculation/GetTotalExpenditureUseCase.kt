package com.jaideep.expensetracker.domain.usecase.calculation

import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.domain.repository.EtRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTotalExpenditureUseCase @Inject constructor(
    private val etRepository: EtRepository
) {
    suspend operator fun invoke() = flow {
        emit(Resource.Loading())
        val totalExpenditure = etRepository.getTotalExpenditure()
        emit(Resource.Success(totalExpenditure))
    }.catch {
        emit(Resource.Error("Error while fetching total expenditure"))
    }
}