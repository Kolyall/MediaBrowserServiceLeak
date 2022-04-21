package com.github.kolya.myapplication.ui.connector.callbacks

import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.github.kolya.myapplication.ext.appPlaybackState
import com.github.kolya.myapplication.models.AppPlaybackStatePosition

class MediaControllerCallback constructor(private val callbackListener: OnMediaControllerCallbackListener) :
    MediaControllerCompat.Callback() {

    override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
        callbackListener.onPlaybackStateChanged(
            AppPlaybackStatePosition(
                state = state.appPlaybackState,
                currentPosition = state?.position ?: 0
            )
        )
    }

    fun onPlaybackStateChanged(state: AppPlaybackStatePosition) {
        callbackListener.onPlaybackStateChanged(state)
    }

    override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
        callbackListener.onMetadataChanged(metadata)
    }

    interface OnMediaControllerCallbackListener {

        fun onPlaybackStateChanged(playbackState: AppPlaybackStatePosition)

        fun onMetadataChanged(metadata: MediaMetadataCompat?)
    }
}
