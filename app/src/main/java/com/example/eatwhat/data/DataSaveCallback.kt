package com.example.eatwhat.data

import java.lang.Exception

interface DataSaveCallback {
    fun success()
    fun error(e:Exception)
}