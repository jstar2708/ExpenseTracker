package com.jaideep.expensetracker.data.local.repositoryimpl

import com.jaideep.expensetracker.data.local.dao.NotificationDao
import com.jaideep.expensetracker.data.local.entities.Notification
import com.jaideep.expensetracker.domain.repository.NotificationRepository
import com.jaideep.expensetracker.model.dto.NotificationDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationDao: NotificationDao
) : NotificationRepository {
    override suspend fun saveNotification(notification: Notification) {
        notificationDao.saveNotification(notification)
    }

    override suspend fun deleteNotification(notification: Notification) {
        notificationDao.deleteNotification(notification)
    }

    override suspend fun getAllNotifications(): Flow<List<NotificationDto>> {
        return notificationDao.getAllNotifications()
    }
}