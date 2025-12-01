package com.example.streaming_top_video_tv.service

import android.app.Notification
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.OptIn
import androidx.core.app.NotificationCompat
import androidx.media3.common.util.NotificationUtil
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.offline.Download
import androidx.media3.exoplayer.offline.DownloadManager
import androidx.media3.exoplayer.offline.DownloadNotificationHelper
import androidx.media3.exoplayer.offline.DownloadService
import androidx.media3.exoplayer.scheduler.Scheduler
import com.example.streaming_top_video_tv.R
import com.example.streaming_top_video_tv.presentation.home.StreamingMovie
import com.example.streaming_top_video_tv.util.Constants.CANCEL_DOWNLOAD
import com.example.streaming_top_video_tv.util.Constants.CANCEL_REQUEST_ID
import com.example.streaming_top_video_tv.util.Constants.CHANNEL_ID
import com.example.streaming_top_video_tv.util.Constants.DOWNLOAD_NOTIFICATION_CHANNEL_ID
import com.example.streaming_top_video_tv.util.Constants.FOREGROUND_NOTIFICATION_ID
import com.example.streaming_top_video_tv.util.Constants.MOVIE_MODEL
import com.example.streaming_top_video_tv.util.Constants.START_DOWNLOAD
import com.example.streaming_top_video_tv.util.DownloadManagerUtil
import com.example.streaming_top_video_tv.util.Utils.sendEventBus

@OptIn(UnstableApi::class)
class DownloadMovieService : DownloadService(
    FOREGROUND_NOTIFICATION_ID,
    DEFAULT_FOREGROUND_NOTIFICATION_UPDATE_INTERVAL,
    CHANNEL_ID,
    R.string.exo_download_notification_channel_name,
    0
) {

    private val streamingMovieMap = mutableMapOf<String, StreamingMovie>()

    companion object {
        var isRunning: Boolean = false
    }

    override fun onCreate() {
        super.onCreate()
        isRunning = true

        // Foreground service notification - simple for Android TV
        startForeground(
            FOREGROUND_NOTIFICATION_ID,
            createInitialNotification()
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when (intent?.action) {

            START_DOWNLOAD -> {
                val movie: StreamingMovie? =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        intent.getSerializableExtra(MOVIE_MODEL, StreamingMovie::class.java)
                    } else {
                        @Suppress("DEPRECATION")
                        intent.getSerializableExtra(MOVIE_MODEL) as? StreamingMovie
                    }

                movie?.let {
                    val request = DownloadManagerUtil.buildDownloadRequest(
                        it.id.toString(),
                        it.streamUrl
                    )
                    sendAddDownload(this, DownloadMovieService::class.java, request, false)
                    streamingMovieMap[it.id.toString()] = it
                }
            }

            CANCEL_DOWNLOAD -> {
                val id = intent.getStringExtra(CANCEL_REQUEST_ID)
                id?.let { downloadId ->

                    val manager = getDownloadManager()
                    manager.removeDownload(downloadId)

                    val hasActiveDownloads = manager.currentDownloads.any {
                        it.state == Download.STATE_DOWNLOADING || it.state == Download.STATE_QUEUED
                    }

                    if (!hasActiveDownloads) {
                        stopForegroundSafe()
                        stopSelf()
                        return START_NOT_STICKY
                    }
                }
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        isRunning = false
        super.onDestroy()
    }

    override fun getDownloadManager(): DownloadManager {
        val manager = DownloadManagerUtil.getDownloadManager(this)
        val helper = DownloadManagerUtil.getDownloadNotificationHelper(this)

        manager.addListener(
            TerminalStateNotificationHelper(
                this,
                helper,
                FOREGROUND_NOTIFICATION_ID + 1,
                streamingMovieMap


        ) {
            val hasActive = manager.currentDownloads.any {
                it.state == Download.STATE_DOWNLOADING || it.state == Download.STATE_QUEUED
            }
            if (!hasActive) {
                stopForegroundSafe()
                stopSelf()
            }
        }
        )

        return manager
    }

    override fun getScheduler(): Scheduler? = null

    override fun getForegroundNotification(
        downloads: List<Download>,
        notMetRequirements: Int
    ): Notification {

        val active = downloads.firstOrNull {
            it.state == Download.STATE_DOWNLOADING || it.state == Download.STATE_QUEUED
        }

        if (active == null) {
            stopForegroundSafe()
            stopSelf()
            return createInitialNotification()
        }

        sendEventBus(this, active)

        return NotificationCompat.Builder(this, DOWNLOAD_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_download_notif)
            .setContentTitle("Downloading...")
            .setContentText(streamingMovieMap[active.request.id]?.title ?: "")
            .setOnlyAlertOnce(true)
            .setOngoing(true)
            .setSilent(true)
            .build()
    }

    private fun stopForegroundSafe() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            stopForeground(STOP_FOREGROUND_REMOVE)
        }
    }

    private fun createInitialNotification(): Notification {
        return NotificationCompat.Builder(this, DOWNLOAD_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_download_notif)
            .setContentTitle("Preparing download...")
            .setOngoing(true)
            .setSilent(true)
            .build()
    }

    private class TerminalStateNotificationHelper(
        context: Context,
        private val notificationHelper: DownloadNotificationHelper,
        private var nextId: Int,
        private val movieMap: MutableMap<String, StreamingMovie>,
        private val onAllDownloadsFinished: () -> Unit
    ) : DownloadManager.Listener {

        private val appContext = context.applicationContext

        override fun onDownloadChanged(
            downloadManager: DownloadManager,
            download: Download,
            finalException: Exception?
        ) {
            sendEventBus(appContext, download)

            val title = movieMap[download.request.id]?.title ?: ""

            val notif = when (download.state) {

                Download.STATE_COMPLETED ->
                    notificationHelper.buildDownloadCompletedNotification(
                        appContext,
                        R.drawable.outline_check_24,
                        null,
                        "$title downloaded successfully."
                    )

                Download.STATE_FAILED ->
                    notificationHelper.buildDownloadFailedNotification(
                        appContext,
                        R.drawable.outline_close_24,
                        null,
                        "Failed: $title"
                    )

                else -> return
            }

            NotificationUtil.setNotification(appContext, nextId++, notif)
        }

        override fun onDownloadRemoved(downloadManager: DownloadManager, download: Download) {
            sendEventBus(appContext, download)
            onAllDownloadsFinished()
        }
    }
}