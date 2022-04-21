package com.github.kolya.myapplication.ui.connector.callbacks

import com.github.kolya.myapplication.models.MetadataNullPlaybackState

interface MediaSessionStateProvider {

    fun getMediaSessionState(): MetadataNullPlaybackState
}
