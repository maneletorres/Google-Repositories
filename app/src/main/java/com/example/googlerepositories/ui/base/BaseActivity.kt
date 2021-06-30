package com.example.googlerepositories.ui.base

import androidx.appcompat.app.AppCompatActivity
import com.example.googlerepositories.util.LogUtil
import com.example.googlerepositories.util.Utils.getMethodName
import com.example.googlerepositories.util.VIEW_EMOJI

abstract class BaseActivity : AppCompatActivity() {
    private val tag: String = VIEW_EMOJI + this::class.simpleName

    fun trackMethod(any: Any) {
        LogUtil.i("$tag ${getMethodName(any)}")
    }
}