package com.jaideep.expensetracker.domain.usecase

import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.common.constant.TransactionMethod
import com.jaideep.expensetracker.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetInitialTransactionsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    private val TRANSACTION_COUNT = 5
    suspend operator fun invoke(
        accountName: String?, startDate: Long?, endDate: Long?, transactionMethod: TransactionMethod
    ) = flow {
        emit(Resource.Loading())
        val account = accountName ?: ""
        val sDate = startDate ?: 0
        val eDate = endDate ?: 0
        val transactions = when (transactionMethod) {
            TransactionMethod.GET_TRANSACTIONS_FOR_ACCOUNT -> transactionRepository.getTransactionsForAccount(
                account, TRANSACTION_COUNT
            )

            TransactionMethod.GET_DEBIT_TRANSACTIONS_FOR_ACCOUNT -> transactionRepository.getDebitTransactionsForAccount(
                account, TRANSACTION_COUNT
            )

            TransactionMethod.GET_CREDIT_TRANSACTIONS_FOR_ACCOUNT -> transactionRepository.getCreditTransactionsForAccount(
                account, TRANSACTION_COUNT
            )

            TransactionMethod.GET_CREDIT_TRANSACTIONS_BETWEEN_DATES_FOR_ACCOUNT -> transactionRepository.getCreditTransactionBetweenDatesForAccount(
                account, sDate, eDate, TRANSACTION_COUNT
            )

            TransactionMethod.GET_DEBIT_TRANSACTIONS_BETWEEN_DATES_FOR_ACCOUNT -> transactionRepository.getDebitTransactionBetweenDatesForAccount(
                account, sDate, eDate, TRANSACTION_COUNT
            )

            TransactionMethod.GET_TRANSACTIONS_BETWEEN_DATES_FOR_ACCOUNT -> transactionRepository.getTransactionBetweenDatesForAccount(
                account, sDate, eDate, TRANSACTION_COUNT
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

            TransactionMethod.GET_CREDIT_TRANSACTIONS_BETWEEN_DATES -> transactionRepository.getCreditTransactionBetweenDates(
                sDate, eDate, TRANSACTION_COUNT
            )

            TransactionMethod.GET_DEBIT_TRANSACTIONS_BETWEEN_DATES -> transactionRepository.getDebitTransactionBetweenDates(
                sDate, eDate, TRANSACTION_COUNT
            )

            TransactionMethod.GET_TRANSACTIONS_BETWEEN_DATES -> transactionRepository.getTransactionBetweenDates(
                sDate, eDate, TRANSACTION_COUNT
            )
        }
        transactions.collect {
            emit(Resource.Success(it))
        }
    }.catch {
        emit(Resource.Error("Error occurred while loading transactions, message: ${it.message}"))
    }
}
