package com.jaideep.expensetracker.domain.usecase

import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.domain.repository.AccountRepository
import com.jaideep.expensetracker.model.dto.AccountDto
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class GetAllAccountsUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke() = accountRepository.getAccounts().map {
            it.map { account ->
                AccountDto(
                    account.id,
                    account.name,
                    account.balance,
                    account.createdOn
                )
            }.toList()
        }.transform<List<AccountDto>, Resource<List<AccountDto>>> {
            emit(Resource.Success(it))
        }.onStart {
            emit(Resource.Loading())
        }.catch {
            emit(Resource.Error("Error while fetching accounts, ${it.message}"))
        }
}