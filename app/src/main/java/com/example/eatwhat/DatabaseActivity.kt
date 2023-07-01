package com.example.eatwhat

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class DatabaseActivity: AppCompatActivity() {
    private val tag = "DatabaseActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database) // 列表布局
        // setSupportActionBar(findViewById(R.id.toolbar))
        Log.i(tag,"Create DatabaseActivity")

    }
}