package com.github.kolya.myapplication.models

enum class AppPlaybackStateEntity {
    STATE_NONE,
    STATE_BUFFERING,
    STATE_PLAYING,
    STATE_STOPPED,
    STATE_PAUSED,
    STATE_SKIPPING_TO_QUEUE_ITEM,
    STATE_REWINDING,
    STATE_SKIPPING_TO_PREVIOUS,
    STATE_FAST_FORWARDING,
    STATE_CONNECTING,
    STATE_SKIPPING_TO_NEXT,
    STATE_ERROR, ;

    companion object {

        const val APP_PLAYBACK_STATE: String = "APP_PLAYBACK_STATE"
    }
}
