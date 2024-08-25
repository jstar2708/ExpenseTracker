package com.jaideep.expensetracker.domain.usecase.calculation

import android.util.Log
import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.domain.repository.EtRepository
import com.jaideep.expensetracker.presentation.utility.Utility
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAmountSpentTodayForAccountUseCase @Inject constructor(
    private val etRepository: EtRepository
) {
    suspend operator fun invoke(accountName: String) = flow {
        emit(Resource.Loading())
        val currentDate = Utility.getCurrentDateInMillis()
        val getAmountSpent =
            if (accountName == "All Accounts") etRepository.getAmountSpentTodayForAllAccount(currentDate)
            else etRepository.getAmountSpentTodayForAccount(accountName, currentDate)

        getAmountSpent.collect {
            emit(Resource.Success(it))
        }
    }.catch { it ->
        emit(Resource.Error("Error while fetching amount spend today ${it}"))
    }
}