package com.jaideep.expensetracker.di

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.jaideep.expensetracker.worker.EtWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class EtApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder().setWorkerFactory(workerFactory).build()

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        initializeWorker()
    }

    private fun createNotificationChannel() {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as? NotificationManager
        notificationManager?.createNotificationChannel(
            NotificationChannel(
                "channel_events", "Event Channel", NotificationManager.IMPORTANCE_HIGH
            )
        )
    }

    private fun initializeWorker() {
        val workManager = WorkManager.getInstance(this)
        val workRequest = PeriodicWorkRequestBuilder<EtWorker>(24, TimeUnit.HOURS).build()
        workManager.enqueueUniquePeriodicWork(
            "notification_worker", ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE, workRequest
        )
    }
}