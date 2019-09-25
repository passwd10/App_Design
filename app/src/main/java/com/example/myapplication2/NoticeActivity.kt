package com.example.myapplication2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_notice.*

class NoticeActivity : AppCompatActivity() {

    private val adapter by lazy {
        NoticeListAdapter()
    }
    private val pref by lazy {
        this.getPreferences(0)
    }
    private val editor by lazy {
        pref.edit()
    }

    var noticeSize = 0 //알림창 RecyclerView 개수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)

        btn_back_to_main.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        noticeSize = pref.getInt("noticeSize", 0)

        //데이터 저장하기
        rerere.setOnClickListener {
            adapter.additem(NoticeItem(R.drawable.flash, "행운퀴즈", "새로운 깜짝퀴즈가 열렸습니다.", "광고"))

            editor.putString("noticeTitle${noticeSize}", adapter.items[noticeSize].noticeTitle).apply()
            editor.putString("noticeContents${noticeSize}", adapter.items[noticeSize].noticeContents).apply()
            editor.putString("noticeDate${noticeSize}", adapter.items[noticeSize].noticeDate).apply()

            noticeSize++

            editor.putInt("noticeSize", noticeSize).apply() //알림창 크기


        }


        //데이터 불러오기
        for (i in 0..noticeSize - 1) {
            adapter.additem(
                NoticeItem(R.drawable.flash,
                    pref.getString("noticeTitle${i}", "없음").toString(),
                    pref.getString("noticeContents${i}", "없음").toString(),
                    pref.getString("noticeDate${i}", "없음").toString()
                )
            )
        }

        rv_notice_list.layoutManager =
            LinearLayoutManager(this) as RecyclerView.LayoutManager? //LayoutManager 선언
        rv_notice_list.adapter = adapter
        rv_notice_list.setHasFixedSize(true)

    }

    fun addNotices() {
        adapter.additem(
            NoticeItem(
                R.drawable.flash,
                "3200원 입금",
                "박인서님이 3,200원을 송금했습니다.",
                "2019/08/24"
            )
        )
        adapter.additem(
            NoticeItem(
                R.drawable.flash,
                "200원 입금",
                "박윤서님이 200원을 송금했습니다.",
                "2019/08/24"
            )
        )
        adapter.additem(
            NoticeItem(
                R.drawable.flash,
                "10,00원 입금",
                "김진서님이 10,000원을 송금했습니다.",
                "2019/08/24"
            )
        )
    }

}
