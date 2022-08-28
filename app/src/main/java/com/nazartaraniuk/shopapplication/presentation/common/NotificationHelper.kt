package com.nazartaraniuk.shopapplication.presentation.common

import android.content.Context
import androidx.work.*
import com.nazartaraniuk.shopapplication.MainActivity
import com.nazartaraniuk.shopapplication.presentation.NotificationWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NotificationHelper @Inject constructor(
    private val context: Context
) {

    fun stopSendingNotifications() {
        WorkManager.getInstance(context).cancelUniqueWork(MainActivity.UNIQUE_WORK_NAME)
    }

    fun startSendingNotifications() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()
        val workRequest = PeriodicWorkRequest.Builder(
            NotificationWorker::class.java,
            REPEAT_INTERVAL,
            TimeUnit.MINUTES
        ).setConstraints(constraints)
            .addTag(MainActivity.UNIQUE_WORK_NAME)
            .build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            MainActivity.UNIQUE_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    companion object {
        const val REPEAT_INTERVAL = 360L
    }
}