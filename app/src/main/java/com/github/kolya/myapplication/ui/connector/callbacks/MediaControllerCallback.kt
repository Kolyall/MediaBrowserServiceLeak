package com.github.kolya.myapplication.ui.connector.callbacks

import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat

class MediaControllerCallback constructor(private val callbackListener: OnMediaControllerCallbackListener) :
    MediaControllerCompat.Callback() {

    override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
        callbackListener.onPlaybackStateChanged(state)
    }

    override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
        callbackListener.onMetadataChanged(metadata)
    }

    interface OnMediaControllerCallbackListener {

        fun onPlaybackStateChanged(playbackState: PlaybackStateCompat?)

        fun onMetadataChanged(metadata: MediaMetadataCompat?)
    }
}
