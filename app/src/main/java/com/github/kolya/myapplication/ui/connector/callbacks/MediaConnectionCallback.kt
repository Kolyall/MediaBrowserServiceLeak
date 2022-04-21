package com.github.kolya.myapplication.ui.connector.callbacks

import android.os.RemoteException
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log

class MediaConnectionCallback constructor(
    private val onMediaConnectionCallbackListener: OnMediaConnectionCallbackListener,
    private val mediaBrowserTokenProvider: MediaBrowserTokenProvider
) : MediaBrowserCompat.ConnectionCallback() {

    override fun onConnected() {
        try {
            onMediaConnectionCallbackListener.onSessionConnected(mediaBrowserTokenProvider.sessionToken)
        } catch (e: RemoteException) {
            Log.e(TAG, "could not connect media controller", e)
            onMediaConnectionCallbackListener.onSessionDisconnected()
        }
    }

    interface OnMediaConnectionCallbackListener {

        @Throws(RemoteException::class)
        fun onSessionConnected(token: MediaSessionCompat.Token)

        fun onSessionDisconnected()
    }

    companion object {

        const val TAG: String = "MediaConnectionCallback"
    }
}
