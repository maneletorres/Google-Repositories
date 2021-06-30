package com.example.googlerepositories.util

object Utils {

    fun getMethodName(any: Any): String = "${any.javaClass.enclosingMethod?.name}"
}