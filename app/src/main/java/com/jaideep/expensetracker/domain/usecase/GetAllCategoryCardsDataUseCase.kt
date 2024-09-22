package com.jaideep.expensetracker.domain.usecase

import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.domain.repository.EtRepository
import com.jaideep.expensetracker.model.CategoryCardData
import com.jaideep.expensetracker.presentation.utility.Utility
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class GetAllCategoryCardsDataUseCase @Inject constructor(
    private val etRepository: EtRepository
) {
    suspend operator fun invoke(accountName: String, duration: String) =
        if (accountName == "All Accounts") {
            etRepository.getCategoryCardsDataFromAllAccountsWithinDuration(
                if (duration == "This Month") Utility.getStartDateOfMonthInMillis()
                else Utility.getStartDateOfYearInMillis()
            )
        } else {
            etRepository.getCategoryCardsDataFromAccountWithinDuration(
                accountName, if (duration == "This Month") Utility.getStartDateOfMonthInMillis()
                else Utility.getStartDateOfYearInMillis()
            )
        }.transform<List<CategoryCardData>, Resource<List<CategoryCardData>>> {
            emit(Resource.Success(it))
        }.onStart {
            emit(Resource.Loading())
        }.catch {
            emit(Resource.Error("Error while fetching the category card data, ${it.message}"))
        }
}