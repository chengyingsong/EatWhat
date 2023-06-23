package com.example.eatwhat.data

import androidx.room.Database
import androidx.room.RoomDatabase


// TODO: 预填充数据库
@Database(entities = [Restaurant::class,EatingLog::class],version = 1,exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    val myDao:MyDao by lazy { createLogDao() }
    abstract fun createLogDao() :MyDao
}