package com.example.eatwhat.data

import androidx.room.ColumnInfo
import  androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Restaurants")
class Restaurant() {
    @PrimaryKey(autoGenerate = false) var name: String = "" // 餐厅名字
    @ColumnInfo(name = "score") var score:Float = 0.0F
    @ColumnInfo(name = "frequency") var frequency:String = ""
    @ColumnInfo(name = "label") var label:String = ""


    //TODO: score和 Frequency改成枚举类
    constructor(name:String,score:Float,frequency:String,label:String): this() {
        this.name = name
        this.score = score
        this.frequency = frequency
        this.label = label
    }

    override fun toString(): String {
        return "name = $name, score = $score, frequency = $frequency, label = $label;\n"
    }
}