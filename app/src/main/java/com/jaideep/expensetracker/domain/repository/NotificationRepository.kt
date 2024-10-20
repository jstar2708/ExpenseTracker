package com.jaideep.expensetracker.domain.repository

import com.jaideep.expensetracker.data.local.entities.Notification
import com.jaideep.expensetracker.model.dto.NotificationDto
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    suspend fun saveNotification(notification: Notification)
    suspend fun deleteNotification(notification: Notification)
    suspend fun getAllNotifications(): Flow<List<NotificationDto>>
}