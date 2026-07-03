/**
 * @license
 * SPDX-License-Identifier: Apache-2.0
 */

package com.kegel.app

import android.os.Bundle
import com.getcapacitor.BridgeActivity

class MainActivity : BridgeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize periodic notification check worker on app startup
        // This ensures notifications are checked even when the app is closed
        NotificationScheduler.schedulePeriodicNotificationCheck(this)
    }
}
