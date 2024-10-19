package com.jaideep.expensetracker.domain.usecase

import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.domain.repository.NotificationRepository
import com.jaideep.expensetracker.model.dto.NotificationDto
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class GetAllNotificationUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke() = notificationRepository.getAllNotifications()
        .transform<List<NotificationDto>, Resource<List<NotificationDto>>> {
            emit(Resource.Success(it))
        }.onStart { emit(Resource.Loading()) }
        .catch {
            emit(Resource.Error("Error while retrieving notifications"))
        }
}