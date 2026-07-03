/**
 * @license
 * SPDX-License-Identifier: Apache-2.0
 */

package com.kegel.app

import android.content.Context
import android.webkit.JavascriptInterface
import org.json.JSONArray

/**
 * JavaScript Bridge to sync schedule from React/TypeScript to Android SharedPreferences.
 * This allows the Kotlin background worker to access the schedule even when the app is closed.
 */
class NotificationBridge(private val context: Context) {
    
    @JavascriptInterface
    fun syncScheduleToNative(scheduleJson: String, dateString: String) {
        try {
            val sharedPrefs = context.getSharedPreferences("kegel_prefs", Context.MODE_PRIVATE)
            sharedPrefs.edit().apply {
                putString("kegel_daily_schedule", scheduleJson)
                putString("kegel_schedule_date", dateString)
                apply()
            }
            android.util.Log.d("NotificationBridge", "Schedule synced to SharedPreferences")
        } catch (e: Exception) {
            android.util.Log.e("NotificationBridge", "Error syncing schedule", e)
        }
    }
}
