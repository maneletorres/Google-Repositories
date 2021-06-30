package com.example.googlerepositories.util

import android.util.Log

object LogUtil {

    private const val TAG = "GoogleRepositories"

    fun i(msg: String?, tag: String = TAG) {
        Log.i(tag, msg ?: "No message")
    }
}