package com.github.kolya.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import com.github.kolya.myapplication.R
import com.github.kolya.myapplication.ui.connector.MediaBrowserConnector
import com.github.kolya.myapplication.ui.connector.callbacks.MediaConnectionCallback
import com.github.kolya.myapplication.ui.connector.callbacks.MediaControllerCallback
import com.github.kolya.myapplication.models.AppPlaybackStatePosition
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity(), MediaConnectionCallback.OnMediaConnectionCallbackListener,
    MediaControllerCallback.OnMediaControllerCallbackListener {

    private var mediaBrowserConnector: MediaBrowserConnector by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mediaBrowserConnector = MediaBrowserConnector(this, this, this)
        lifecycle.addObserver(mediaBrowserConnector)
    }

    override fun onSessionConnected(token: MediaSessionCompat.Token) {
        Log.d(TAG, "onSessionConnected: ")
    }

    override fun onSessionDisconnected() {
        Log.d(TAG, "onSessionDisconnected: ")
    }

    override fun onPlaybackStateChanged(playbackState: AppPlaybackStatePosition) {
        Log.d(TAG, "onPlaybackStateChanged: $playbackState")
    }

    override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
        Log.d(TAG, "onMetadataChanged: $metadata")
    }

    companion object {
        const val TAG: String = "MainActivity"
    }
}