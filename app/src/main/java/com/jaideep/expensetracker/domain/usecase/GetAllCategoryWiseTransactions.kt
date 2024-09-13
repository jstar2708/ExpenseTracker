package com.jaideep.expensetracker.domain.usecase

import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.domain.repository.EtRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllCategoryWiseTransactions @Inject constructor(
    private val etRepository: EtRepository
) {
    suspend operator fun invoke(categoryName: String, accountName: String, fromDate: Long, toDate: Long) = flow {
        emit(Resource.Loading())
        val data =
            if (accountName == "All Accounts") etRepository.getCategoryWiseAllAccountTransactionsWithDate(
                categoryName, fromDate, toDate
            )
            else etRepository.getCategoryWiseAccountTransactionWithDate(
                categoryName, accountName, fromDate, toDate
            )

        data.collectLatest {
            emit(Resource.Success(it))
        }
    }.catch {
        emit(Resource.Error("Error while fetching category specific transactions"))
    }
}