package com.jaideep.expensetracker.worker

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.jaideep.expensetracker.R
import com.jaideep.expensetracker.domain.repository.NotificationRepository
import com.jaideep.expensetracker.model.dto.NotificationDto
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import java.time.LocalDate

@HiltWorker
class EtWorker @AssistedInject constructor(
    @Assisted app: Context,
    @Assisted workParams: WorkerParameters,
    private val notificationRepository: NotificationRepository
) : CoroutineWorker(app, workParams) {

    override suspend fun doWork(): Result {
        try {
            val notificationDtoList: List<NotificationDto> =
                notificationRepository.getAllNotifications().first().filter { notificationDto ->
                    notificationDto.date.isEqual(LocalDate.now())
                }
            val notificationManager =
                applicationContext.getSystemService(NOTIFICATION_SERVICE) as? NotificationManager
            notificationDtoList.forEachIndexed { index, notificationDto ->
                val notification = Notification.Builder(applicationContext, "channel_events")
                    .setContentTitle("General reminder").setContentText(notificationDto.message)
                    .setSmallIcon(R.drawable.app_icon).build()


                notificationManager?.notify(index, notification)
            }
            return Result.success()
        } catch (ex: Exception) {
            return Result.failure()
        }
    }
}