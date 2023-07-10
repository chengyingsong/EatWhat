package com.example.eatwhat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.eatwhat.data.DataParam
import com.example.eatwhat.data.DataUtil
import com.example.eatwhat.data.Restaurant
import kotlinx.android.synthetic.main.activity_database.*
import java.lang.Exception

class DatabaseActivity: AppCompatActivity() {
    private val tag = "DatabaseActivity"
    var restaurantList: MutableList<Restaurant> = mutableListOf()
    val context:Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database) // 列表布局
        // setSupportActionBar(findViewById(R.id.toolbar))
        Log.i(tag,"Create DatabaseActivity")

        showData()

        restRecyclerview.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        val adapter = RecyclerViewAdapter(restaurantList)
        adapter.setonClickListener(object:RecyclerViewAdapter.OnClickListener {
            override fun onRemoveButtonClicked(view: View, position: Int) {
                // 删除
                Log.i(tag,"Click Remove Button")
                DataUtil.removeRest(restaurantList[position],object:DataUtil.DataCallback{
                    override fun success() {
                        Log.i("Database", "删除成功：" +restaurantList[position].name)
                        val msg = Message()
                        msg.what = DataParam.DELETE_SUCCESS
                        msg.obj = position
                        mHandler.sendMessage(msg)
                    }
                    override fun error(e: Exception) {
                        Log.e("Database",e.toString()+"删除错误")
                        val msg = Message()
                        msg.what = DataParam.DELETE_ERROR
                        msg.obj = position
                        mHandler.sendMessage(msg)
                    }

                })
            }
            override fun onUpdateButtonClicked(view: View, position: Int) {
                // 更新 --> 转入更新界面

                // adapter.notifyItemChanged(position)
            }
        })
        restRecyclerview.adapter = adapter

        add_button.setOnClickListener{
            startActivity(Intent(this,AddDataActivity::class.java))
        }

        return_button.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }

        show_button.setOnClickListener {
            showData()
        }

    }
    private fun showData(){
        DataUtil.getAllRests(object : DataUtil.DataQueryCallback {
            override fun success(rests: MutableList<Restaurant>) {
                for (rest in rests){
                    Log.i(tag,rest.toString())
                }
                restaurantList = rests
                val msg = Message()
                msg.what = DataParam.GET_SUCCESS
                mHandler.sendMessage(msg)
            }

            override fun error(exception: Exception) {
                Log.e(tag,exception.toString())
                val msg = Message()
                msg.what = DataParam.GET_ERROR
                mHandler.sendMessage(msg)
            }

        })

    }

    private val mHandler = object : Handler(Looper.getMainLooper()){
        // 获取数据并显示
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what){
                DataParam.GET_SUCCESS -> {
                    // 显示数据
                    (restRecyclerview.adapter as RecyclerViewAdapter).setItems(restaurantList)
                    (restRecyclerview.adapter as RecyclerViewAdapter).notifyDataSetChanged()
                }
                DataParam.GET_ERROR -> { Toast.makeText(context,"获取数据失败", Toast.LENGTH_SHORT).show()}
                DataParam.DELETE_SUCCESS -> {
                    // 删除数据
                    val position:Int = msg.obj as Int
                    restaurantList.removeAt(position)
                    (restRecyclerview.adapter as RecyclerViewAdapter).notifyItemRemoved(position)
                }
                DataParam.DELETE_ERROR -> {
                    Toast.makeText(context,"删除数据失败", Toast.LENGTH_SHORT).show()}
                else -> {Log.e("Database","未识别信号${msg.what}")}
            }
        }
    }

}