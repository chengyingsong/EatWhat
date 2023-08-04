package com.example.eatwhat.data

import androidx.room.ColumnInfo
import  androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "Restaurants")
class Restaurant():Serializable {
    @PrimaryKey(autoGenerate = false) var name: String = "" // 餐厅名字
    @ColumnInfo(name = "score") var score:Float = 0.0F
    @ColumnInfo(name = "frequency") var frequency:Int = 0
    @ColumnInfo(name = "label") var label:Int = 0


    //
    constructor(name:String,score:Float,frequency:Int,label:Int): this() {
        this.name = name
        this.score = score
        this.frequency = frequency
        this.label = label
    }

    constructor(name:String,score:Float,label:Int): this() {
        this.name = name
        this.score = score
        this.label = label
    }

    override fun toString(): String {
        return "name = $name, score = $score, frequency = $frequency, label = $label;\n"
    }

    fun setEmpty(){
        name = ""
        score = 0.0F
        frequency = 0
        label = 0
    }
}