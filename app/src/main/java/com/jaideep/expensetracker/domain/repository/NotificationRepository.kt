package com.jaideep.expensetracker.domain.repository

import com.jaideep.expensetracker.data.local.entities.Notification
import com.jaideep.expensetracker.model.dto.NotificationDto
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    suspend fun saveNotification(notification: NotificationDto)
    suspend fun deleteNotification(notification: NotificationDto)
    suspend fun getAllNotifications(): Flow<List<NotificationDto>>
}