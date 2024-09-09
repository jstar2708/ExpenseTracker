package com.jaideep.expensetracker.domain.usecase

import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.domain.repository.EtRepository
import com.jaideep.expensetracker.presentation.utility.Utility
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllCategoryCardsDataUseCase @Inject constructor(
    private val etRepository: EtRepository
) {
    suspend operator fun invoke(accountName: String, duration: String) = flow {
        emit(Resource.Loading())
        val data = if (accountName == "All Accounts") {
            etRepository.getCategoryCardsDataFromAllAccountsWithinDuration(
                if (duration == "This Month") Utility.getStartDateOfMonthInMillis()
                else Utility.getStartDateOfYearInMillis()
            )
        } else {
            etRepository.getCategoryCardsDataFromAccountWithinDuration(
                accountName, if (duration == "This Month") Utility.getStartDateOfMonthInMillis()
                else Utility.getStartDateOfYearInMillis()
            )
        }
        data.collectLatest {
            emit(Resource.Success(it))
        }
    }.catch {
        emit(Resource.Error("Error while fetching the category card data"))
    }
}