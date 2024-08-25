package com.jaideep.expensetracker.domain.usecase

import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.common.constant.TransactionMethod
import com.jaideep.expensetracker.domain.repository.TransactionRepository
import com.jaideep.expensetracker.model.TransactionMethodData
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetInitialTransactionsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    private val TRANSACTION_COUNT = 5
    suspend operator fun invoke(
        transactionMethodData: TransactionMethodData
    ) = flow {
        emit(Resource.Loading())
        val account = transactionMethodData.accountName
        val transactions = when (transactionMethodData.transactionMethod) {
            TransactionMethod.GET_TRANSACTIONS_FOR_ACCOUNT -> transactionRepository.getTransactionsForAccount(
                account, TRANSACTION_COUNT
            )

            TransactionMethod.GET_DEBIT_TRANSACTIONS_FOR_ACCOUNT -> transactionRepository.getDebitTransactionsForAccount(
                account, TRANSACTION_COUNT
            )

            TransactionMethod.GET_CREDIT_TRANSACTIONS_FOR_ACCOUNT -> transactionRepository.getCreditTransactionsForAccount(
                account, TRANSACTION_COUNT
            )

            TransactionMethod.GET_ALL_TRANSACTIONS -> transactionRepository.getAllTransactions(
                TRANSACTION_COUNT
            )

            TransactionMethod.GET_DEBIT_TRANSACTIONS -> transactionRepository.getDebitTransactions(
                TRANSACTION_COUNT
            )

            TransactionMethod.GET_CREDIT_TRANSACTIONS -> transactionRepository.getCreditTransactions(
                TRANSACTION_COUNT
            )
            else -> transactionRepository.getAllTransactions(
                TRANSACTION_COUNT
            )
        }
        transactions.collect {
            emit(Resource.Success(it))
        }
    }.catch {
        emit(Resource.Error("Error occurred while loading transactions, message: ${it.message}"))
    }
}
