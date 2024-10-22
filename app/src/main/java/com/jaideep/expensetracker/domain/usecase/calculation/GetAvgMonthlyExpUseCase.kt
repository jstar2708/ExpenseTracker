package com.jaideep.expensetracker.domain.usecase.calculation

import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.domain.repository.EtRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.time.Period
import javax.inject.Inject

class GetAvgMonthlyExpUseCase @Inject constructor(
    private val etRepository: EtRepository
) {
    suspend operator fun invoke() = flow {
        emit(Resource.Loading())
        val lastDate = etRepository.getLastTransactionDate()
        val firstDate = etRepository.getFirstTransactionDate()
        if (firstDate == null || lastDate == null) {
            emit(Resource.Success(0))
        } else {
            val numberOfMonths = Period.between(firstDate, lastDate).toTotalMonths()
            if (numberOfMonths == 0L) {
                emit(Resource.Success(etRepository.getTotalExpenditure()))
            } else {
                emit(Resource.Success(etRepository.getTotalExpenditure() / numberOfMonths))
            }
        }
    }.catch { it ->
        emit(Resource.Error("Error while fetching the average monthly expenditure ${it.message}"))
    }
}