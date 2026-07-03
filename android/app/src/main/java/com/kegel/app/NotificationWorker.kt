/**
 * @license
 * SPDX-License-Identifier: Apache-2.0
 */

package com.kegel.app

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import org.json.JSONArray
import org.json.JSONObject

class NotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        return try {
            val context = applicationContext
            val sharedPrefs = context.getSharedPreferences("kegel_prefs", Context.MODE_PRIVATE)
            
            // Get stored schedule from SharedPreferences
            val scheduleJson = sharedPrefs.getString("kegel_daily_schedule", null)
            val scheduleDateStr = sharedPrefs.getString("kegel_schedule_date", "")
            val todayString = getTodayDateString()
            
            // Only check if schedule is for today
            if (scheduleJson != null && scheduleDateStr == todayString) {
                val schedule = JSONArray(scheduleJson)
                val now = System.currentTimeMillis()
                
                for (i in 0 until schedule.length()) {
                    val notification = schedule.getJSONObject(i)
                    val timestamp = notification.getLong("timestamp")
                    val status = notification.getString("status")
                    
                    // Check if notification should trigger (within 2 minute window)
                    if (status == "pending" && Math.abs(timestamp - now) < 120000) {
                        showNotification(
                            context,
                            notification.getInt("id"),
                            notification.getString("title"),
                            notification.getString("message"),
                            notification.getString("type")
                        )
                    }
                }
            }
            
            Result.success()
        } catch (e: Exception) {
            android.util.Log.e("NotificationWorker", "Error in notification worker", e)
            Result.retry()
        }
    }
    
    private fun showNotification(
        context: Context,
        notificationId: Int,
        title: String,
        message: String,
        type: String
    ) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "kegel_notifications"
        
        // Create notification channel for Android 8+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, "Kegel & Meditação", importance).apply {
                description = "Notificações de exercícios e meditação"
                enableVibration(true)
                // Set vibration pattern based on type
                vibrationPattern = if (type == "kegel") {
                    longArrayOf(200, 100, 200) // Stronger pattern for Kegel
                } else {
                    longArrayOf(100, 50, 100)  // Softer pattern for Meditation
                }
                // Use default notification sound
                setSound(
                    android.media.RingtoneManager.getDefaultUri(android.media.RingtoneManager.TYPE_NOTIFICATION),
                    android.media.AudioAttributes.Builder()
                        .setUsage(android.media.AudioAttributes.USAGE_NOTIFICATION)
                        .build()
                )
            }
            notificationManager.createNotificationChannel(channel)
        }
        
        // Create intent to open app when notification is tapped
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("notificationId", notificationId)
            putExtra("activityType", type)
        }
        
        val pendingIntent = PendingIntent.getActivity(
            context,
            notificationId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        // Build and show notification
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setVibrate(if (type == "kegel") longArrayOf(200, 100, 200) else longArrayOf(100, 50, 100))
            .build()
        
        notificationManager.notify(notificationId, notification)
    }
    
    private fun getTodayDateString(): String {
        val cal = java.util.Calendar.getInstance()
        val sdf = java.text.SimpleDateFormat("EEE MMM dd yyyy", java.util.Locale.US)
        return sdf.format(cal.time)
    }
}
