package com.example.todayonly.notification

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.todayonly.R

class ReminderReceiver : BroadcastReceiver() {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onReceive(context: Context, intent: Intent) {

        val title = intent.getStringExtra(EXTRA_TASK_TITLE) ?: return
        val id = intent.getLongExtra(EXTRA_TASK_ID, -1)

        if(id == -1L) return

        val notification = NotificationCompat.Builder(
            context,
            "task_reminders"
        ).setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Task Reminder")
            .setContentText(title)
            .setPriority(
                NotificationCompat.PRIORITY_HIGH
            )
            .build()

        NotificationManagerCompat
            .from(context)
            .notify(
                id.toInt(),
                notification
            )
    }

    companion object {
        const val EXTRA_TASK_ID = "extra_task_id"
        const val EXTRA_TASK_TITLE = "extra_task_title"
    }
}