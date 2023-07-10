package com.example.eatwhat.data

import android.os.Looper
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

object DataUtil {
    interface DataCallback {
        fun success()
        fun error(e:Exception)
    }

    interface DataQueryCallback {
        fun success(rests:MutableList<Restaurant>)
        fun error(e: Exception)
    }

    fun saveRest(rest:Restaurant, callback: DataCallback) {
        GlobalScope.launch {
            try{
                DatabaseManager.db.myDao.save(rest)
                callback.success()
            }catch (e:Exception){
                callback.error(e)
            }
        }
    }

    fun getAllRests(callback: DataQueryCallback) {
        GlobalScope.launch {
            try{
                val rests:MutableList<Restaurant> = DatabaseManager.db.myDao.getAllRests()
                Log.i("Database", "获取所有餐厅列表")
                callback.success(rests)
            }catch (e:Exception){
                Log.e("Database", "getAllRests:$e")
                callback.error(e)
            }
        }
    }

    fun removeRest(rest: Restaurant,callback: DataCallback){
        GlobalScope.launch {
            try{
                DatabaseManager.db.myDao.delete(rest)
                callback.success()
            }catch (e:Exception){
                callback.error(e)
            }
        }
    }
    fun updateRest(rest: Restaurant,callback: DataCallback){
        GlobalScope.launch {
            try{
                DatabaseManager.db.myDao.update(rest)
                callback.success()
            }catch (e:Exception){
                callback.error(e)
            }
        }
    }
}