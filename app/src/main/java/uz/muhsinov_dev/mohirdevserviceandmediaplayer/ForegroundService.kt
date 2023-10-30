package uz.muhsinov_dev.mohirdevserviceandmediaplayer

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder

class ForegroundService : Service() {

    private val CHANNEL_ID = "channel_id"

    private var mediaPlayer: MediaPlayer? = null
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        mediaPlayer = MediaPlayer.create(this, R.raw.joni)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when (intent?.getIntExtra("code", -1)) {
            START -> {
                mediaPlayer?.start()
            }

            PAUSE -> {
                mediaPlayer?.apply {
                    if (isPlaying) {
                        this.pause()
                    } else {
                        this.start()
                    }
                }
            }

            STOP -> {
                mediaPlayer?.stop()
                stopSelf()
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                CHANNEL_ID,
                "ChannelName",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            channel.lightColor = Color.GREEN
            channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
                channel
            )

            val notification = Notification.Builder(this, CHANNEL_ID)
                .setContentTitle("This is the Title")
                .setContentText("This is the Text")
                .setOnlyAlertOnce(true)
                .setSmallIcon(R.drawable.ic_stat_name)

                .setCategory(Notification.CATEGORY_SERVICE)
                .build()

            startForeground(1111, notification)


        }


        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        val START = 0
        val PAUSE = 1
        val STOP = 2
    }


}
