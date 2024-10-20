package com.jaideep.expensetracker.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jaideep.expensetracker.common.constant.sql.NotificationSql.GET_ALL_NOTIFICATIONS
import com.jaideep.expensetracker.data.local.entities.Notification
import com.jaideep.expensetracker.model.dto.NotificationDto
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNotification(notification: Notification)

    @Delete
    suspend fun deleteNotification(notification: Notification)

    @Query(GET_ALL_NOTIFICATIONS)
    fun getAllNotifications(): Flow<List<NotificationDto>>
}