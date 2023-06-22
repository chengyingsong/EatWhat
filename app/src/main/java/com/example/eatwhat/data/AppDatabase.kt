package com.example.eatwhat.data

import androidx.room.Database
import androidx.room.RoomDatabase


// TODO: java.lang.RuntimeException: cannot find implementation for com.example.eatwhat.Data.AppDatabase. AppDatabase_Impl does not exist_
@Database(entities = [Restaurant::class,EatingLog::class],version = 1,exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    val myDao:MyDao by lazy { createLogDao() }
    abstract fun createLogDao() :MyDao
}