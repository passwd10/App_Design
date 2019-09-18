package com.example.myapplication2

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.android.synthetic.main.notice_item.view.*
import kotlinx.android.synthetic.main.notice_item_rem.view.*
import org.w3c.dom.Text
import java.lang.IllegalArgumentException

class NoticeListAdapter : RecyclerView.Adapter<ViewHolder>() {

    val AD = 0 // 광고
    val REM = 1 //송금

    var items: MutableList<NoticeItem> = mutableListOf() //컬렉션 동적 리스트

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder  {

        return when(viewType) {
            AD -> NoticeListHolder(LayoutInflater.from(parent.context).inflate(R.layout.notice_item, parent, false)) //광고
            REM ->  NoticeListRemHolder(LayoutInflater.from(parent.context).inflate(R.layout.notice_item_rem, parent, false)) //송금내역
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        when(holder) {
            is NoticeListHolder -> {
                holder.tvNoticeImageView.setImageResource(items[position].noticeImageResources)
                holder.tvNoticeTitle.text = items[position].noticeTitle
                holder.tvNoticeContents.text = items[position].noticeContents
                holder.tvNoticeAd.text = items[position].noticeDate
            }
            is NoticeListRemHolder -> {
                holder.tvNoticeRemImageView.setImageResource(items[position].noticeImageResources)
                holder.tvNoticeRemTitle.text = items[position].noticeTitle
                holder.tvNoticeRemContents.text = items[position].noticeContents
                holder.tvNoticeRemDate.text = items[position].noticeDate
            }
        }

    }

    fun additem(item: NoticeItem) {
        items.add(item)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        val comparable = items[position].noticeDate
        return when(comparable.length) {
            2 -> AD
            else -> REM
        }
    }

    class NoticeListHolder(itemView: View) : ViewHolder(itemView) {
        //광고
        var tvNoticeImageView: ImageView = itemView.iv_notice_image //이미지
        var tvNoticeTitle: TextView = itemView.tv_notice_title //제목
        var tvNoticeContents: TextView = itemView.tv_notice_contents //내용
        var tvNoticeAd: TextView = itemView.tv_notice_ad //광고
    }

    class NoticeListRemHolder(itemView: View) : ViewHolder(itemView) {
        //송금내역
        var tvNoticeRemImageView: ImageView = itemView.iv_notice_rem_image //이미지
        var tvNoticeRemTitle: TextView = itemView.tv_notice_money //제목
        var tvNoticeRemContents: TextView = itemView.tv_notice_rem_contents //내용
        var tvNoticeRemDate: TextView = itemView.tv_notice_date //날짜
    }
}