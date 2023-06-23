package com.example.eatwhat

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.example.eatwhat.data.DataUtil
import com.example.eatwhat.data.Restaurant
import com.example.eatwhat.data.DataSaveCallback
import kotlinx.android.synthetic.main.fragment_second.*
import java.lang.Exception

class SecondFragment : Fragment() {

    var restEntity:Restaurant = Restaurant()
    companion object{
        const val ADD_SUCCESS = 1001
        const val ADD_ERROR   = 1002
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adaptSpinner(Spinner_restaurant_frequency,true)
        adaptSpinner(Spinner_restaurant_label,false)


        button_set.setOnClickListener {
            val restName:String = EditText_add_restaurant_name.text.toString()
            val restScoreString:String = EditText_add_restaurant_score.text.toString()
            var restScore = 0.0F
            var add2Database = true
            try{
                restScore = restScoreString.toFloat()
            }catch (e:NumberFormatException){
                // 重新输入
                Toast.makeText(context,"请输入0-5之间的浮点数作为分数",Toast.LENGTH_SHORT).show()
                add2Database = false
            }
            if (add2Database) {
                restEntity.name = restName
                restEntity.score = restScore
                // 检查是否未输入
                if(checkEmpty()){
                    DataUtil.saveRest(restEntity,object: DataSaveCallback {
                        override fun success() {
                            Log.i("Database", "添加成功：" + restEntity.name)
                            val msg = Message()
                            msg.what = ADD_SUCCESS
                            mHandler.sendMessage(msg)
                        }

                        override fun error(e: Exception) {
                            Log.e("Database",e.toString()+"_添加错误")
                            val msg = Message()
                            msg.what = ADD_ERROR
                            mHandler.sendMessage(msg)
                        }
                    })
                }
            }


        }

        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    private val mHandler = object :Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what){
                ADD_SUCCESS -> {
                    Toast.makeText(context, "添加成功！", Toast.LENGTH_SHORT).show()
                    // 清空restEntity,恢复默认值
                    restEntity.name = ""
                    restEntity.score = -1.0F
                    restEntity.frequency = Spinner_restaurant_frequency.adapter.getItem(0).toString()
                    restEntity.label = Spinner_restaurant_label.adapter.getItem(0).toString()

                    //清空输入框,恢复默认值
                    EditText_add_restaurant_name.text = null
                    EditText_add_restaurant_score.text = null
                    Spinner_restaurant_frequency.setSelection(0)
                    Spinner_restaurant_label.setSelection(0)
                }
                ADD_ERROR -> { Toast.makeText(context,"出现错误",Toast.LENGTH_SHORT).show()}
                else -> {Log.e("Database","未识别信号${msg.what}")}
            }
        }
    }

    private fun checkEmpty(): Boolean {
        if(restEntity.name == ""){
            Toast.makeText(context,"请输入餐厅名字",Toast.LENGTH_SHORT).show()
            return false
        }else if (restEntity.score > 5F || restEntity.score < 0F){
            Toast.makeText(context,"请输入0-5之间的浮点数作为分数",Toast.LENGTH_SHORT).show()
            return false
        } else if (restEntity.frequency == "") {
            Toast.makeText(context,"请选择去餐厅的频率",Toast.LENGTH_SHORT).show()
            return false
        } else if (restEntity.label == ""){
            Toast.makeText(context,"请选择餐厅的类别",Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun adaptSpinner(spinner:Spinner, isfrequency:Boolean){
        var array = R.array.frequencyArray
        if (!isfrequency){
            array = R.array.labelArray
        }

        // 设置适配器
        ArrayAdapter.createFromResource(
            context!!,
            array,
            android.R.layout.simple_spinner_item
        ).also{ adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = (object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val s:String = parent?.getItemAtPosition(position).toString()
                if(isfrequency) {
                    restEntity.frequency = s
                }else{
                    restEntity.label = s
                }
                Toast.makeText(context,s,Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        })

    }

}