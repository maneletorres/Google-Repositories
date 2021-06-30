package com.example.googlerepositories.ui.base

import androidx.fragment.app.Fragment
import com.example.googlerepositories.util.LogUtil
import com.example.googlerepositories.util.Utils.getMethodName

abstract class BaseFragment : Fragment() {

    abstract fun setupFallbackBehaviour()

    fun trackMethod(any: Any) {
        LogUtil.i("$tag ${getMethodName(any)}")
    }
}