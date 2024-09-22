package com.jaideep.expensetracker.domain.usecase

import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.common.constant.AppConstants
import com.jaideep.expensetracker.common.constant.TransactionMethod
import com.jaideep.expensetracker.domain.repository.TransactionRepository
import com.jaideep.expensetracker.model.TransactionMethodData
import com.jaideep.expensetracker.model.dto.TransactionDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class GetInitialTransactionsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(
        transactionMethodData: TransactionMethodData
    ): Flow<Resource<List<TransactionDto>>> {
        val account = transactionMethodData.accountName
        val transactions = when (transactionMethodData.transactionMethod) {
            TransactionMethod.GET_TRANSACTIONS_FOR_ACCOUNT -> transactionRepository.getTransactionsForAccount(
                account, AppConstants.TRANSACTION_COUNT
            )

            TransactionMethod.GET_DEBIT_TRANSACTIONS_FOR_ACCOUNT -> transactionRepository.getDebitTransactionsForAccount(
                account, AppConstants.TRANSACTION_COUNT
            )

            TransactionMethod.GET_CREDIT_TRANSACTIONS_FOR_ACCOUNT -> transactionRepository.getCreditTransactionsForAccount(
                account, AppConstants.TRANSACTION_COUNT
            )

            TransactionMethod.GET_ALL_TRANSACTIONS -> transactionRepository.getAllTransactions(
                AppConstants.TRANSACTION_COUNT
            )

            TransactionMethod.GET_DEBIT_TRANSACTIONS -> transactionRepository.getDebitTransactions(
                AppConstants.TRANSACTION_COUNT
            )

            TransactionMethod.GET_CREDIT_TRANSACTIONS -> transactionRepository.getCreditTransactions(
                AppConstants.TRANSACTION_COUNT
            )

            else -> transactionRepository.getAllTransactions(
                AppConstants.TRANSACTION_COUNT
            )
        }.transform<List<TransactionDto>, Resource<List<TransactionDto>>> {
            emit(Resource.Success(it))
        }.onStart {
            emit(Resource.Loading())
        }.catch {
            emit(Resource.Error("Error occurred while loading transactions, message: ${it.message}"))
        }
        return transactions
    }
}
