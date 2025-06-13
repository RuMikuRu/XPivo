package com.example.xpivo.data.cache

object FreeCache {
    fun clear() {
        this.token = null
        this.userId = null
    }

    var token:String? = null
    var userId:Int? = null
}