package com.example.eatwhat.data

import android.os.Looper
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

object DataUtil {


    fun saveRest(rest:Restaurant, callback: DataSaveCallback) {
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
                val rests:List<Restaurant> = DatabaseManager.db.myDao.getAllRests()
                Log.i("Database", "获取所有餐厅列表")
                callback.success(rests)
            }catch (e:Exception){
                Log.e("Database", "getAllRests:$e")
                callback.error(e)
            }
        }
    }
}