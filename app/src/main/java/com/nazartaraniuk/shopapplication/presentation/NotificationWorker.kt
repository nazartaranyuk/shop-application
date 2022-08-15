package com.nazartaraniuk.shopapplication.presentation

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.nazartaraniuk.shopapplication.MainActivity
import com.nazartaraniuk.shopapplication.R

class NotificationWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {
        showNotification()
        return Result.success()
    }

    private fun showNotification() {
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent =
            PendingIntent.getActivity(applicationContext, REQUEST_CODE, intent, FLAGS)
        val notification = Notification.Builder(
            applicationContext,
            CHANNEL_ID
        ).setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("We have a sales!!! 50%!! Black Friday!!")
            .setContentText("Buy some products in our super shop!")
            .setPriority(Notification.PRIORITY_MAX) // TODO replace this deprecated
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channelImportance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, channelImportance).apply {
                description = CHANNEL_DESCRIPTION
            }

            val notificationManager = applicationContext
                    .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        with(NotificationManagerCompat.from(applicationContext)) {
            notify(NOTIFY_ID, notification.build())
        }

    }

    companion object {
        const val REQUEST_CODE = 0
        const val FLAGS = 0
        const val NOTIFY_ID = 1
        const val CHANNEL_ID = "Channel id"
        const val CHANNEL_NAME = "Channel name"
        const val CHANNEL_DESCRIPTION = "Channel description"
    }
}