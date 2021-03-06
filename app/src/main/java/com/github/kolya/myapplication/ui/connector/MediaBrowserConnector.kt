package com.github.kolya.myapplication.ui.connector

import android.app.Activity
import android.content.ComponentName
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.github.kolya.myapplication.service.MusicService
import com.github.kolya.myapplication.ui.connector.callbacks.MediaBrowserTokenProvider
import com.github.kolya.myapplication.ui.connector.callbacks.MediaConnectionCallback
import com.github.kolya.myapplication.ui.connector.callbacks.MediaControllerCallback
import kotlin.properties.Delegates

class MediaBrowserConnector constructor(
    private val activity: Activity,
    private val mediaConnectionCallbackListener: MediaConnectionCallback.OnMediaConnectionCallbackListener,
    mediaControllerCallbackListener: MediaControllerCallback.OnMediaControllerCallbackListener
) : DefaultLifecycleObserver,
    MediaConnectionCallback.OnMediaConnectionCallbackListener,
    MediaBrowserTokenProvider {

    private val applicationContext = activity.applicationContext

    private var mediaBrowser: MediaBrowserCompat by Delegates.notNull()
    private val mediaControllerCallback = MediaControllerCallback(mediaControllerCallbackListener)

    private var mediaController: MediaControllerCompat? = null

    override val sessionToken: MediaSessionCompat.Token
        get() = mediaBrowser.sessionToken

    private val isMediaBrowserConnected: Boolean
        get() = mediaBrowser.isConnected

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        Log.i(TAG, "onCreate")
        mediaBrowser = MediaBrowserCompat(
            applicationContext,
            ComponentName(applicationContext, MusicService::class.java),
            MediaConnectionCallback(this, this),
            null
        )
        connect()
        registerControllerCallback()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        unregisterControllerCallback()
        disconnect()
        super.onDestroy(owner)
    }

    private fun connect() {
        if (!isMediaBrowserConnected) {
            mediaBrowser.connect()
        }
    }

    override fun onSessionConnected(token: MediaSessionCompat.Token) {
        mediaController = MediaControllerCompat(applicationContext, token)
            .also {
                activity.setMediaControllerCompat(it)
            }
        registerControllerCallback()
        mediaConnectionCallbackListener.onSessionConnected(token)
        mediaControllerCallback.onMetadataChanged(mediaController?.metadata)
        mediaControllerCallback.onPlaybackStateChanged(mediaController?.playbackState)
    }

    override fun onSessionDisconnected() {
        activity.setMediaControllerCompat(null)
        mediaConnectionCallbackListener.onSessionDisconnected()
    }

    private fun disconnect() {
        if (isMediaBrowserConnected) {
            mediaBrowser.disconnect()
        }
    }

    private fun registerControllerCallback() {
        mediaController?.registerCallback(mediaControllerCallback)
    }

    private fun unregisterControllerCallback() {
        mediaController?.unregisterCallback(mediaControllerCallback)
    }

    companion object {

        const val TAG: String = "MediaControllerManager"
    }

    private fun Activity.setMediaControllerCompat(mediaController: MediaControllerCompat?) {
        MediaControllerCompat.setMediaController(this, mediaController)
    }

}

