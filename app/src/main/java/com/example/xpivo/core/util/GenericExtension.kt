package com.example.xpivo.core.util

import android.util.Log
import kotlin.jvm.javaClass

fun <T> T.log(text:String) {
    Log.d((this as Any).javaClass.simpleName, text)
}