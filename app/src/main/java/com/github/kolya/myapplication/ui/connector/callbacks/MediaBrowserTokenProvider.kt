package com.github.kolya.myapplication.ui.connector.callbacks

import android.support.v4.media.session.MediaSessionCompat

interface MediaBrowserTokenProvider {

    val sessionToken: MediaSessionCompat.Token
}
