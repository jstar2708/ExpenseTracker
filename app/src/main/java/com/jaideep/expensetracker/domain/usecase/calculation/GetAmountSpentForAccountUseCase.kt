package com.jaideep.expensetracker.domain.usecase.calculation

import com.jaideep.expensetracker.domain.repository.EtRepository
import javax.inject.Inject

class GetAmountSpentForAccountUseCase @Inject constructor(
    private val etRepository: EtRepository
) {
    suspend operator fun invoke(accountName: String): Double {
        return etRepository.getAmountSpentTodayForAccount(accountName)
    }
}