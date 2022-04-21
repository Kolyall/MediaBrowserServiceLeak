package com.github.kolya.myapplication.ext

import android.app.Activity
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.github.kolya.myapplication.models.AppPlaybackStateEntity
import com.github.kolya.myapplication.models.AppPlaybackStatePosition
import com.github.kolya.myapplication.models.MetadataNullPlaybackState

fun AppPlaybackStateEntity?.orDefault(): AppPlaybackStateEntity {
    return this ?: AppPlaybackStateEntity.STATE_NONE
}

fun Activity.setMediaControllerCompat(mediaController: MediaControllerCompat?) {
    MediaControllerCompat.setMediaController(this, mediaController)
}

fun Pair<MediaMetadataCompat?, PlaybackStateCompat?>.toMediaSessionState(): MetadataNullPlaybackState {
    return MetadataNullPlaybackState(first, second.toAppPlaybackStatePosition())
}

fun PlaybackStateCompat?.toAppPlaybackStatePosition(): AppPlaybackStatePosition {
    val currentPosition = this?.currentPosition ?: 0
    return AppPlaybackStatePosition(appPlaybackState, currentPosition)
}

val PlaybackStateCompat?.appPlaybackState: AppPlaybackStateEntity
    get() {
        return this?.extras?.getInt(AppPlaybackStateEntity.APP_PLAYBACK_STATE)?.let { AppPlaybackStateEntity.values()[it] }
            .orDefault()
    }

val PlaybackStateCompat?.currentPosition: Long
    get() {
        val playbackState = this
        val isPlaying = playbackState?.appPlaybackState?.isPlaying != true
        val isOnPause = playbackState?.appPlaybackState?.isOnPause != true
        val position =
            if (isPlaying && isOnPause) {
                0L
            } else {
                playbackState?.position ?: 0L
            }

        return position
    }


inline val AppPlaybackStateEntity.isPlaying: Boolean
    get() {
        return when (this) {
            AppPlaybackStateEntity.STATE_NONE,
            AppPlaybackStateEntity.STATE_ERROR,
            AppPlaybackStateEntity.STATE_STOPPED,
            AppPlaybackStateEntity.STATE_PAUSED,
            AppPlaybackStateEntity.STATE_SKIPPING_TO_NEXT,
            AppPlaybackStateEntity.STATE_SKIPPING_TO_QUEUE_ITEM,
            AppPlaybackStateEntity.STATE_SKIPPING_TO_PREVIOUS,
            AppPlaybackStateEntity.STATE_FAST_FORWARDING,
            AppPlaybackStateEntity.STATE_REWINDING -> false

            AppPlaybackStateEntity.STATE_BUFFERING,
            AppPlaybackStateEntity.STATE_PLAYING,
            AppPlaybackStateEntity.STATE_CONNECTING -> true
        }
    }

inline val AppPlaybackStateEntity.isOnPause: Boolean
    get() {
        return when (this) {
            AppPlaybackStateEntity.STATE_PAUSED -> true
            else -> false
        }
    }