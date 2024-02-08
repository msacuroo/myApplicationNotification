package com.example.myapplication

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.getSystemService
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    companion object{
        private val CHANNEL_ID = "notification_channel_id"
        private val notificationId = 121
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChannel()
        val buttonNotify = findViewById<Button>(R.id.button_notify)
        val fabNotificationSettings =
            findViewById<FloatingActionButton>(R.id.fab_notification_settings)
        buttonNotify.setOnClickListener{
            fabNotificationSettings.setImageResource(R.drawable.ic_notification)
            sendNotification("This is created")
        }
    }

        fun openNotificationChannelSettings() {
            val channelId = "my_channel_id" // Replace with your actual channel ID
            val intent = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS).apply {
                putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                putExtra(Settings.EXTRA_CHANNEL_ID, channelId)
            }
            startActivity(intent)
        }



    fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O){
            val name = "Notification Channel"
            val descriptionText = "Notifying"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID,name,importance).apply {
                description =descriptionText
            }

            val notificationManager:NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("MissingPermission")
    fun sendNotification(notificationText :String){
        val builder = NotificationCompat.Builder(this,CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Notification Title")
            .setContentText(notificationText)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(this)){
            notify(notificationId,builder.build())
        }

    }
}