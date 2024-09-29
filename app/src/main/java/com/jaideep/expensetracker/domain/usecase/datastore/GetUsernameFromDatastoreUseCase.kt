package com.jaideep.expensetracker.domain.usecase.datastore

import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.common.constant.AppConstants.USER_NAME
import com.jaideep.expensetracker.data.local.preferences.DatastoreRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUsernameFromDatastoreUseCase @Inject constructor(
    private val datastoreRepository: DatastoreRepository
) {
    suspend operator fun invoke() = flow {
        emit(Resource.Loading())
        val username = datastoreRepository.getString(USER_NAME)
        if (username.isNullOrEmpty()) {
            emit(Resource.Error("Username not found"))
        } else {
            emit(Resource.Success(username))
        }
    }.catch {
        emit(Resource.Error("Error while fetching username"))
    }
}