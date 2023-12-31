package com.example.eatwhat.data

import androidx.room.*


//使用协程
@Dao
interface MyDao {
    @Insert
    suspend fun save(vararg rest:Restaurant)

    @Insert
    suspend fun saveLog(vararg logs:EatingLog)

    @Delete
    suspend fun delete(vararg rest:Restaurant) : Integer

    @Update
    suspend fun update(vararg rest:Restaurant)

    @Query("select * from Restaurants")
    suspend fun getAllRests(): MutableList<Restaurant>


    // Query注解中是SQL语句
    @Query("select time from EatingLog order by time asc limit 1")
    suspend fun getFirstLogTime(): Long

    @Query("select time from EatingLog order by time desc limit 1")
    suspend fun getLastLogTime(): Long

    @Query("select * from EatingLog where time>=:startTime and time <=:endTime")
    suspend fun getLogByFilter(startTime: Long, endTime: Long): MutableList<EatingLog>


}