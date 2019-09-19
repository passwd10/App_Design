package com.example.myapplication2

import android.app.Application
import android.app.ProgressDialog.show
import android.content.Context
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_time_line.*
import kotlinx.android.synthetic.main.activity_time_line.view.*
import kotlinx.android.synthetic.main.timeline_item.view.*
import org.w3c.dom.Text
import java.lang.IllegalArgumentException

class TimeLineAdapter : RecyclerView.Adapter<TimeLineAdapter.TimelineViewHolder>() {

    var items: MutableList<TimeLineItem> = mutableListOf(
        TimeLineItem(R.drawable.question, "10000", "맥도날드", "12", "24"),
        TimeLineItem(R.drawable.question, "5000", "롯데리아", "14", "10"),
        TimeLineItem(R.drawable.question, "10000", "맥도날드", "12", "24"),
        TimeLineItem(R.drawable.question, "5000", "롯데리아", "14", "10")
    )


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.timeline_item, parent, false)
        return TimelineViewHolder(view)

    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TimelineViewHolder, position: Int) {
        holder.timeLineImageView.setImageResource(items[position].timelineImgSource)
        holder.timeLineMoney.text = items[position].timeLineMoney
        holder.timeLineContents.text = items[position].timeLineContents
        holder.timeLineHours.text = items[position].timeLineHours
        holder.timeLineMinutes.text = items[position].timeLineMinutes
        holder.timeLineWhole.setOnCreateContextMenuListener(holder)

    }

    fun addItems(item: TimeLineItem) {
        items.add(item)
        notifyDataSetChanged()
    }

    fun deleteItems(itemPosition: Int) {
        items.removeAt(itemPosition)
        notifyDataSetChanged()
    }

    class TimelineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnCreateContextMenuListener {


        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            var revise = menu?.add(Menu.NONE, R.id.revise_list, 1, "수정")
            var delete = menu?.add(Menu.NONE, R.id.delete_list, 2, "삭제")
        }

        var timeLineImageView: ImageView = itemView.iv_timeline_img
        var timeLineMoney: TextView = itemView.tv_timeline_money
        var timeLineContents: TextView = itemView.tv_timeline_contents
        var timeLineHours: TextView = itemView.tv_timeline_hour
        var timeLineMinutes: TextView = itemView.tv_timeline_minute
        var timeLineWhole: ConstraintLayout = itemView.cl_timeline


    }

}