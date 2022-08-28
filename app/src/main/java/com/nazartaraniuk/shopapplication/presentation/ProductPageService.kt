package com.nazartaraniuk.shopapplication.presentation

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_HIGH
import com.nazartaraniuk.shopapplication.MainActivity
import com.nazartaraniuk.shopapplication.R
import com.nazartaraniuk.shopapplication.presentation.pdp_screen.ProductPageFragment.Companion.PRODUCT_NAME_KEY
import com.nazartaraniuk.shopapplication.presentation.pdp_screen.ProductPageFragment.Companion.PRODUCT_PRICE_KEY

class ProductPageService : Service() {

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent) {
            null -> startForeground()
            else -> startForeground(intent)
        }
        return START_STICKY
    }

    private fun startForeground(intent: Intent? = null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }

        val productName = intent?.getStringExtra(PRODUCT_NAME_KEY) ?: UNKNOWN
        val productPrice = intent?.getStringExtra(PRODUCT_PRICE_KEY) ?: UNKNOWN

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
        val notification = notificationBuilder.setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(PRIORITY_HIGH)
            .setContentText("$CONTENT_TEXT $productPrice")
            .setContentTitle(productName)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()

        startForeground(101, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_NONE
        ).apply {
            lightColor = Color.BLUE
            lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        }

        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).apply {
            createNotificationChannel(channel)
        }
    }
    companion object {
        const val CHANNEL_ID = "channel_id"
        const val CHANNEL_NAME = "product_notification"
        const val CONTENT_TEXT = "Buy this product with price"
        const val UNKNOWN = "Unknown"
    }
}