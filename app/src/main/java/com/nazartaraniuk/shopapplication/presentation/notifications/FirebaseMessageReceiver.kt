package com.nazartaraniuk.shopapplication.presentation.notifications

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.nazartaraniuk.shopapplication.presentation.MainActivity
import com.nazartaraniuk.shopapplication.R


@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class FirebaseMessageReceiver : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        message.notification.let {
            showNotification(
                it?.title,
                it?.body
            )
        }
    }

    private fun showNotification(title: String?, message: String?) {
        val intent = Intent(this, MainActivity::class.java)
        val channelId = "notification_channel"
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        var builder: NotificationCompat.Builder = NotificationCompat.Builder(
            applicationContext,
            channelId
        )
            .setSmallIcon(R.drawable.ic_launcher)
            .setAutoCancel(true)
            .setVibrate(
                longArrayOf(
                    1000, 1000, 1000,
                    1000, 1000
                )
            )
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
        builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            builder.setContent(
                getCustomDesign(title, message)
            )
        } else {
            builder.setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_launcher)
        }

        val notificationManager = getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId, "web_app",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(
                notificationChannel
            )
        }

        notificationManager.notify(0, builder.build())
    }

    private fun getCustomDesign(
        title: String?,
        message: String?
    ): RemoteViews {
        return RemoteViews(
            applicationContext.packageName,
            R.layout.notification
        ).apply {
            setTextViewText(R.id.title, title)
            setTextViewText(R.id.message, message)
            setImageViewResource(
                R.id.icon,
                R.drawable.ic_launcher
            )
        }
    }
}