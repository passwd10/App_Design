package com.example.myapplication2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.android.synthetic.main.kaokao_main.view.*
import kotlinx.android.synthetic.main.receiver_item.view.*

class RemittanceAdapter : RecyclerView.Adapter<RemittanceAdapter.RemViewHolder>() {


    var items: MutableList<RemittanceItem> = mutableListOf(
        RemittanceItem(R.drawable.toss, "토스머니", "010-1234-1234"),
        RemittanceItem(R.drawable.toss, "비상금", "토스 미션계좌"),
        RemittanceItem(R.drawable.kbbank, "KB국민은행", "KB국민 514121010101101")
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.receiver_item, parent, false)
        return RemViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RemViewHolder, position: Int) {

        holder.remImg.setImageResource(items[position].remImgSrc)
        holder.remRecvName.text = items[position].remName
        holder.remRecvNum.text = items[position].remNum
    }

    fun addItems(item : RemittanceItem) {
        items.add(item)
        notifyDataSetChanged()
    }

    class RemViewHolder(itemView: View) : ViewHolder(itemView) {
        var remImg: ImageView = itemView.iv_receiver_img
        var remRecvName: TextView = itemView.tv_receiver_name
        var remRecvNum: TextView = itemView.tv_receiver_num
    }
}