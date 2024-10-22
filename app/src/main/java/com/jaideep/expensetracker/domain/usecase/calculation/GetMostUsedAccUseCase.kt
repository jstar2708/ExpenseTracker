package com.jaideep.expensetracker.domain.usecase.calculation

import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.domain.repository.EtRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMostUsedAccUseCase @Inject constructor(
    private val etRepository: EtRepository
) {
    suspend operator fun invoke() = flow {
        emit(Resource.Loading())
        val mostUsedAcc = etRepository.getMostFrequentlyUsedAccount() ?: "None"
        emit(Resource.Success(mostUsedAcc))
    }.catch {
        emit(Resource.Error("Error while fetching the most frequently used account"))
    }
}