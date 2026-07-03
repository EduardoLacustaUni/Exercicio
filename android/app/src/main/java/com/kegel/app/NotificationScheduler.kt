/**
 * @license
 * SPDX-License-Identifier: Apache-2.0
 */

package com.kegel.app

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

object NotificationScheduler {
    const val WORKER_TAG = "kegel_notification_worker"
    
    /**
     * Schedules periodic notification checks to run every 15 minutes.
     * This ensures notifications are checked and shown even if app is in background.
     */
    fun schedulePeriodicNotificationCheck(context: Context) {
        val notificationCheckRequest = PeriodicWorkRequestBuilder<NotificationWorker>(
            15,
            TimeUnit.MINUTES
        )
            .addTag(WORKER_TAG)
            .build()
        
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WORKER_TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            notificationCheckRequest
        )
    }
    
    /**
     * Cancels the periodic notification check.
     */
    fun cancelNotificationCheck(context: Context) {
        WorkManager.getInstance(context).cancelAllWorkByTag(WORKER_TAG)
    }
}
