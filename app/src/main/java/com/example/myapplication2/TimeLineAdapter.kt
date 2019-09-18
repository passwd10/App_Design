package com.example.myapplication2

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_time_line.view.*
import kotlinx.android.synthetic.main.timeline_item.view.*
import org.w3c.dom.Text

class TimeLineAdapter : RecyclerView.Adapter<TimeLineAdapter.TimelineViewHolder>() {

    var items : MutableList<TimeLineItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.timeline_item,parent,false)
        return TimelineViewHolder(view)

    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TimelineViewHolder, position: Int) {
        holder.timeLineImageView.setImageResource(items[position].timelineImgSource)
        holder.timeLineMoney.text = items[position].timeLineMoney
        holder.timeLineContents.text = items[position].timeLineContents
        holder.timeLineTime.text = items[position].timeLineTime

    }

    fun addItems(item: TimeLineItem){
        items.add(item)
        notifyDataSetChanged()
    }

    fun deleteItems(item: TimeLineItem){
        items.remove(item)
        notifyDataSetChanged()
    }


    class TimelineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var timeLineImageView : ImageView = itemView.iv_timeline_img
        var timeLineMoney : TextView = itemView.tv_timeline_money
        var timeLineContents : TextView = itemView.tv_timeline_contents
        var timeLineTime : TextView = itemView.tv_timeline_time

        fun TimeLineContext(){
            itemView.setOnClickListener {
                Log.d("TAG","hi")
            }
        }

    }
}