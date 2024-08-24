package com.jaideep.expensetracker.domain.usecase.calculation

import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.domain.repository.EtRepository
import com.jaideep.expensetracker.model.CategoryCardData
import com.jaideep.expensetracker.presentation.utility.Utility
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCategoryAmountForWhichMaxAmountWasSpentFromAccountUseCase @Inject constructor(
    private val etRepository: EtRepository
) {
    suspend operator fun invoke(accountName: String) = flow {
        emit(Resource.Loading())
        val startOfMonth = Utility.getStartDateOfMonthInMillis()
        val result =
            if (accountName == "All Accounts") etRepository.getCategoryOnWhichMaximumAmountSpentFromAllAccountsThisMonth(
                    startOfMonth
                )
            else etRepository.getCategoryOnWhichMaximumAmountSpentFromAccountThisMonth(
                accountName, startOfMonth
            )

        result.collect {
            emit(Resource.Success(it ?: CategoryCardData("Food", "Food", 0.0)))
        }
    }.catch {
        emit(Resource.Error("Error while fetching the category and amount fro which max amount was spent from account"))
    }
}