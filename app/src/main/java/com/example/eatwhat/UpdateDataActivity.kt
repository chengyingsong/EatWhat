package com.example.eatwhat
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.eatwhat.data.DataParam
import com.example.eatwhat.data.DataUtil
import com.example.eatwhat.data.Restaurant
import kotlinx.android.synthetic.main.activity_update_data.*
import java.lang.Exception

class UpdateDataActivity : AppCompatActivity() {
    var restEntity: Restaurant = Restaurant()
    var originalRest:Restaurant = Restaurant()
    private val tag = "UpdateDataActivity"
    private val context = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_data) // 列表布局
        // setSupportActionBar(findViewById(R.id.toolbar))
        Log.i(tag,"Update Data Activity")
        restEntity = (intent.getSerializableExtra("rest") as Restaurant?)!!
        originalRest = restEntity


        adaptSpinner(Spinner_restaurant_frequency,true)
        adaptSpinner(Spinner_restaurant_label,false)
        // set UpdateName
        setHint(restEntity)



        button_set.setOnClickListener {
            val restName:String = EditText_restaurant_name.text.toString()
            val restScoreString:String = EditText_restaurant_score.text.toString()
            var restScore = 0.0F
            var add2Database = true
            try{
                restScore = restScoreString.toFloat()
            }catch (e:NumberFormatException){
                // 重新输入
                Toast.makeText(this,"请输入0-5之间的浮点数作为分数", Toast.LENGTH_SHORT).show()
                add2Database = false
            }
            if (add2Database) {
                restEntity.name = restName
                restEntity.score = restScore
                // 检查是否未输入
                if(checkEmpty()){
                    DataUtil.updateRest(restEntity,object: DataUtil.DataCallback {
                        override fun success() {
                            Log.i("Database", "更新成功：" + restEntity.name)
                            val msg = Message()
                            msg.what = DataParam.UPDATE_SUCCESS
                            mHandler.sendMessage(msg)
                        }

                        override fun error(e: Exception) {
                            // 根据错误代码提示输入
                            Log.e("Database",e.toString()+"_更新错误")
                            val msg = Message()
                            msg.what = DataParam.UPDATE_ERROR
                            mHandler.sendMessage(msg)
                        }
                    })
                }
            }
        }

        button_return.setOnClickListener{
            startActivity(Intent(this,DatabaseActivity::class.java))
        }
    }

    private val mHandler = object : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what){
                DataParam.UPDATE_SUCCESS -> {
                    Toast.makeText(context, "修改成功！", Toast.LENGTH_SHORT).show()
                    // 清空restEntity,恢复默认值
                    restEntity.setEmpty()

                    //清空输入框,恢复默认值
                    setHint(restEntity)
                }
                DataParam.UPDATE_ERROR -> {
                    restEntity = originalRest
                    Toast.makeText(context,"出现错误", Toast.LENGTH_SHORT).show()}
                else -> {
                    Log.e("Database","未识别信号${msg.what}")}
            }
        }
    }

    private fun setHint(restaurant: Restaurant){
        EditText_restaurant_name.text = if (restaurant.name == "")  null else Editable.Factory.getInstance().newEditable(restaurant.name)
        EditText_restaurant_score.text = if (restaurant.score == -1.0F)  null else Editable.Factory.getInstance().newEditable(
            restaurant.score.toString()
        )
        // find position in the array
        Spinner_restaurant_frequency.setSelection(restEntity.frequency)
        Spinner_restaurant_label.setSelection(restEntity.label)
    }

    private fun checkEmpty(): Boolean {
        if(restEntity.name == ""){
            Toast.makeText(context,"请输入餐厅名字", Toast.LENGTH_SHORT).show()
            return false
        }else if (restEntity.score > 5F || restEntity.score < 0F){
            Toast.makeText(context,"请输入0-5之间的浮点数作为分数", Toast.LENGTH_SHORT).show()
            return false
        } else if (restEntity.frequency == -1) {
            Toast.makeText(context,"请选择去餐厅的频率", Toast.LENGTH_SHORT).show()
            return false
        } else if (restEntity.label == -1){
            Toast.makeText(context,"请选择餐厅的类别", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun adaptSpinner(spinner: Spinner, isfrequency:Boolean){
        var array = R.array.frequencyArray
        if (!isfrequency){
            array = R.array.labelArray
        }

        // 设置适配器
        ArrayAdapter.createFromResource(
            context,
            array,
            android.R.layout.simple_spinner_item
        ).also{ adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(isfrequency) {
                    restEntity.frequency = position
                }else{
                    restEntity.label = position
                }
                Toast.makeText(context,parent?.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        })

    }


}