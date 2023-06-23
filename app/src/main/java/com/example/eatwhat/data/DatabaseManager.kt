package com.example.eatwhat.data

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

//单类
object DatabaseManager {
    private const val DB_NAME = "APPData.db"
    private val MIGRATIONS = arrayOf(Migration1)
    private lateinit var application: Application
    val db: AppDatabase by lazy {
        Room.databaseBuilder(application.applicationContext,AppDatabase::class.java, DB_NAME)
            .addCallback(CreateCallBack)
            .addMigrations(*MIGRATIONS)
            .createFromAsset("database/APPData.db")
            .build()
    }

    fun saveApplication(application: Application){
        DatabaseManager.application = application
    }

    private object CreateCallBack:RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            // 数据库build之后调用。
            MIGRATIONS.map{
                it.migrate(db)
            }
        }
    }

    private object Migration1: Migration(1,2){
        override fun migrate(database: SupportSQLiteDatabase) {
            // 数据库升级
        }
    }

}