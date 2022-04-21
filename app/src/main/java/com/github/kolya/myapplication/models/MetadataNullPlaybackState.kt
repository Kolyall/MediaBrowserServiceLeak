package com.github.kolya.myapplication.models

import android.support.v4.media.MediaMetadataCompat

data class MetadataNullPlaybackState constructor(
    val metadata: MediaMetadataCompat?,
    val playbackState: AppPlaybackStatePosition
)
