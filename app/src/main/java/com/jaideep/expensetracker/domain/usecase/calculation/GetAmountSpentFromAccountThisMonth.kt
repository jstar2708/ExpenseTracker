package com.jaideep.expensetracker.domain.usecase.calculation

import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.domain.repository.EtRepository
import com.jaideep.expensetracker.presentation.utility.Utility
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAmountSpentFromAccountThisMonth @Inject constructor(
    private val etRepository: EtRepository
) {
    suspend operator fun invoke(accountName: String) = flow {
        emit(Resource.Loading())
        val startOfMonth = Utility.getStartDateOfMonthInMillis()
        val result =
            if (accountName == "All Accounts") etRepository.getAmountSpentFromAllAccountThisMonth(
                startOfMonth
            )
            else etRepository.getTotalAmountSpentFromAccountThisMonth(
                accountName, Utility.getStartDateOfMonthInMillis()
            )

        result.collect {
            emit(Resource.Success(it))
        }
    }.catch {
        emit(Resource.Error("Error while getting amount spent from account this month, ${it.message}"))
    }
}