package com.example.eatwhat


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.eatwhat.data.Restaurant


class RecyclerViewAdapter(private var mItems:MutableList<Restaurant>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private lateinit var  mOnClickListener : OnClickListener

    fun setonClickListener(onClickListener: RecyclerViewAdapter.OnClickListener) {
        this.mOnClickListener = onClickListener
    }

    fun setItems(items:MutableList<Restaurant>){
        mItems = items
    }

    interface OnClickListener {
        fun onRemoveButtonClicked(view:View,position: Int)

        fun onUpdateButtonClicked(view: View,position: Int)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // private val mImgRecyItem : ImageView = itemView.findViewById(R.id.icon)
        private val mRestNameText : TextView = itemView.findViewById(R.id.restName)
        private val mScoreText: TextView = itemView.findViewById(R.id.score)
        private val mLabelText: TextView =itemView.findViewById(R.id.label)
        private var currentRest:Restaurant? = null
        val removeButton: Button = itemView.findViewById(R.id.remove_button)
        val updateButton: Button = itemView.findViewById(R.id.update_button)



        fun bind(rest:Restaurant) {
            currentRest = rest

            // mImgRecyItem.setImageResource(R.drawable.rose)
            mRestNameText.text = rest.name
            mScoreText.text = "分数:${rest.score}"
            mLabelText.text = itemView.resources.getStringArray(R.array.labelArray)[rest.label]
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder{
        val v = LayoutInflater.from(parent.context).inflate(R.layout.rest_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.ViewHolder, position: Int) {
        val rest:Restaurant = mItems[position]
        holder.bind(rest)
        holder.removeButton.setOnClickListener {
            // 删除rest
            mOnClickListener.onRemoveButtonClicked(holder.itemView,position)
        }
        holder.updateButton.setOnClickListener{
            // 更新rest
            mOnClickListener.onUpdateButtonClicked(holder.itemView,position)
        }
    }

    override fun getItemCount(): Int {
        return mItems.size
    }




}