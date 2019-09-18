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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)
        Log.i("TAG", "onCreate_notice")

        btn_back_to_main.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            setResult(Activity.RESULT_OK,intent)
            finish()
        }

        rerere.setOnClickListener {
            adapter.additem(NoticeItem(R.drawable.flash,"제목","본문","윙"))
            adapter.additem(NoticeItem(R.drawable.question,"북북","본본","위위"))
        }

        addNotices()

        rv_notice_list.layoutManager =
            LinearLayoutManager(this) as RecyclerView.LayoutManager? //LayoutManager 선언
        rv_notice_list.adapter = adapter
        rv_notice_list.setHasFixedSize(true)

    }

    fun addNotices() {
        adapter.additem(NoticeItem(R.drawable.flash, "3200원 입금", "박인서님이 3,200원을 송금했습니다.","2019/08/24"))
        adapter.additem(NoticeItem(R.drawable.question, "행운퀴즈", "새로운 깜짝퀴즈가 열렸습니다.","광고"))
        adapter.additem(NoticeItem(R.drawable.flash, "200원 입금", "박윤서님이 200원을 송금했습니다.","2019/08/24"))
        adapter.additem(NoticeItem(R.drawable.flash, "10,00원 입금", "김진서님이 10,000원을 송금했습니다.","2019/08/24"))
        adapter.additem(NoticeItem(R.drawable.question, "행운퀴즈", "새로운 깜짝퀴즈가 열렸습니다.","광고"))
    }

    public override fun onStart() {
        super.onStart()
        Log.i("TAG", "onStart_notice")
    }

    public override fun onResume() {
        super.onResume()
        Log.i("TAG", "onResume_notice")
    }

    public override fun onPause() {
        super.onPause()
        Log.i("TAG", "onPause_notice")
    }

    public override fun onRestart() {
        super.onRestart()
        Log.i("TAG", "onRestart_notice")
    }

    public override fun onStop() {
        super.onStop()
        Log.i("TAG", "onStop_notice")
    }

    public override fun onDestroy() {
        super.onDestroy()
        Log.i("TAG", "onDestroy_notice")
    }
}
