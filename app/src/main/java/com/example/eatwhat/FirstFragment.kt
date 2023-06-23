package com.example.eatwhat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.eatwhat.data.DataQueryCallback
import com.example.eatwhat.data.DataUtil
import com.example.eatwhat.data.Restaurant
import kotlinx.android.synthetic.main.fragment_first.*
import java.lang.Exception

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    var restaurantList: List<Restaurant>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_first.setOnClickListener {
            // 获取餐厅列表
            DataUtil.getAllRests(object :DataQueryCallback{
                override fun success(rests: List<Restaurant>) {
                    for (rest in rests){
                        Log.i("FirstFragment",rest.toString())
                    }
                    restaurantList = rests
                }

                override fun error(exception: Exception) {
                    Log.e("FirstFragment",exception.toString())
                    // Toast.makeText(context,"获取数据失败",Toast.LENGTH_SHORT).show()
                }

            })
            val restSize:Int = restaurantList?.size ?:0
            if (restSize == 0) {
                Toast.makeText(context,"可选择餐厅数目为0，请添加！",Toast.LENGTH_SHORT).show()
            } else {
                val random = (0 until restSize).random()
                val name = restaurantList?.get(random)?.name
                Toast.makeText(this.context, "吃$name！！", Toast.LENGTH_SHORT).show()
            }
        }

        button_add_restaurant.setOnClickListener{
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }
}