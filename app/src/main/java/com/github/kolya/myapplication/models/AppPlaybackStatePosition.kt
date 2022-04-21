package com.github.kolya.myapplication.models

data class AppPlaybackStatePosition constructor(
    val state: AppPlaybackStateEntity,
    val currentPosition: Long
)
