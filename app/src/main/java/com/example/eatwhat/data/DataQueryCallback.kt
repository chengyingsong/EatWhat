package com.example.eatwhat.data


import java.lang.Exception

interface DataQueryCallback {
    fun success(rests:List<Restaurant>)
    fun error(e: Exception)
}