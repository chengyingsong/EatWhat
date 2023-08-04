package com.example.eatwhat.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Restaurant::class,EatingLog::class],version = 2,exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    val myDao:MyDao by lazy { createLogDao() }
    abstract fun createLogDao() :MyDao
}