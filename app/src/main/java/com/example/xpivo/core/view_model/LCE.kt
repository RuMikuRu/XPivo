package com.example.xpivo.core.view_model

sealed class Lce<out T> {
    object Loading : Lce<Nothing>()
    data class Ready<T>(val data: T) : Lce<T>()
    data class Content<T>(val data: T) : Lce<T>()
    data class Error(val throwable: Throwable, val retry: (() -> Unit)? = null) : Lce<Nothing>()
}

class SingleEvent<out T>(private val value: T) {
    private var consumed = false
    fun get(): T? = if (consumed) null else {
        consumed = true
        value
    }
}