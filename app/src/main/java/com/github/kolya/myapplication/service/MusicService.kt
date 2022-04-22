package com.github.kolya.myapplication.service

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat.MediaItem
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import androidx.media.MediaBrowserServiceCompat
import androidx.media.session.MediaButtonReceiver
import com.github.kolya.myapplication.R
import com.github.kolya.myapplication.ui.MainActivity
import kotlin.properties.Delegates

class MusicService : MediaBrowserServiceCompat() {

    private var session: MediaSessionCompat by Delegates.notNull()

    override fun onCreate() {
        super.onCreate()
        setupSession()
    }

    private fun setupSession() {
        session = buildSession()
        sessionToken = session.sessionToken
    }

    private val playbackManager = object : MediaSessionCompat.Callback() {

    }

    /**
     * Start a new MediaSession
     */
    private fun buildSession(): MediaSessionCompat {
        return MediaSessionCompat(this, TAG)
            .apply {
                setCallback(playbackManager)
                setFlags(
                    MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS
                            or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
                )
                setSessionActivity(
                    PendingIntent.getActivity(
                        applicationContext,
                        99 /*request code*/,
                        Intent(applicationContext, MainActivity::class.java),
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )
                )
                setExtras(Bundle())
            }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        handleStartIntent(intent)
        return super.onStartCommand(intent, flags, startId)
    }

    private fun handleStartIntent(startIntent: Intent?) {
        if (startIntent == null) return

        /*
        * Try to handle the intent as a media button event wrapped by MediaButtonReceiver
        */
        MediaButtonReceiver.handleIntent(session, startIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "$TAG:onDestroy:stopService ")

        session.release()

        stopForeground(true)
        stopSelf()
    }

    override fun onGetRoot(clientPackageName: String, clientUid: Int, rootHints: Bundle?): BrowserRoot {
        return BrowserRoot(getString(R.string.app_name), null)
    }

    override fun onLoadChildren(parentMediaId: String, result: Result<List<MediaItem>>) {
        result.detach()
    }

    companion object {

        const val TAG: String = "MusicService"
    }
}
