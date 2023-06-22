package com.example.eatwhat.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "EatingLog")
class EatingLog (){
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
    var restaurantName:String = ""
    var time:Long = 0

    constructor(restaurantName:String ):this() {
        time = System.currentTimeMillis()
        this.restaurantName = restaurantName
    }
}