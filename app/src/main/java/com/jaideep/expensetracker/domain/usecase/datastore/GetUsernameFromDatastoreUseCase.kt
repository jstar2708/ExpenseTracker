package com.jaideep.expensetracker.domain.usecase.datastore

import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.common.constant.AppConstants.USER_NAME
import com.jaideep.expensetracker.data.local.preferences.DatastoreRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class GetUsernameFromDatastoreUseCase @Inject constructor(
    private val datastoreRepository: DatastoreRepository
) {
    suspend operator fun invoke() =
        datastoreRepository.getString(USER_NAME)
            .transform<String?, Resource<String?>> { data ->
                emit(Resource.Success(data))
            }.onStart {
                emit(Resource.Loading())
            }.catch {
                emit(Resource.Error("Error while fetching username"))
            }
}