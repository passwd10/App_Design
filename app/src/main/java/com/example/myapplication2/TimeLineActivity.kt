package com.example.myapplication2

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_time_line.*
import kotlinx.android.synthetic.main.timeline_dialog_custom.*
import kotlinx.android.synthetic.main.timeline_dialog_custom.view.*
import kotlinx.android.synthetic.main.toss_main_2.*
import kotlinx.android.synthetic.main.toss_main_2.btn_toss_allset
import kotlinx.android.synthetic.main.toss_main_2.btn_toss_lookup
import kotlinx.android.synthetic.main.toss_main_2.btn_toss_main
import kotlinx.android.synthetic.main.toss_main_2.btn_toss_opened
import java.sql.Time

class TimeLineActivity : AppCompatActivity() {

    val LOOKUP_CODE = 222       //조회
    val OPENED_CODE = 444       //개설
    val AllSET_CODE = 555       //전체설정
    val NOTICE_CODE = 888       //알림

    var itemPosition = 1// 해당아이템 위치

    var timeLineSize = 0 // 타임라인 데이터 크기

    private val adapter by lazy {
        TimeLineAdapter()
    }
    private val pref by lazy {
        this.getPreferences(0)
    }
    private val editor by lazy {
        pref.edit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_line)

        rv_timeline_list.adapter = adapter
        rv_timeline_list.layoutManager = LinearLayoutManager(this)
        rv_timeline_list.hasFixedSize()

        rv_timeline_list.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
            }

            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                if(e.action == MotionEvent.ACTION_DOWN) { //손가락을 땔때 이벤트 발생
                    val child = rv_timeline_list.findChildViewUnder(e.x, e.y)
                    Log.d("TAG", "child ==>" + e.x + " " + e.y)

                    itemPosition = rv_timeline_list.getChildAdapterPosition(child!!)
                    Log.d("TAG", "position ==>" + itemPosition)
                }
                return false
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
            }

        })

        timeLineSize = pref.getInt("TimeLineSize", 0)

        //데이터 불러오기
        if (timeLineSize > 0) {
            for (i in 0..timeLineSize - 1) {
                adapter.addItems(
                    TimeLineItem(
                        R.drawable.question,
                        pref.getString("DialogMoney${i}", "0")!!,
                        pref.getString("DialogContents${i}", "없음")!!,
                        pref.getString("DialogHours${i}", "00")!!,
                        pref.getString("DialogMinutes${i}", "00")!!
                    )
                )
            }
        }

        //내역추가 버튼
        btn_add_timeline.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.timeline_dialog_custom, null)
            builder.setView(dialogView).setPositiveButton("확인") { dialogInterface, i ->

                adapter.addItems(
                    TimeLineItem(
                        R.drawable.question,
                        dialogView.et_dialog_money.text.toString(),
                        dialogView.et_dialog_contents.text.toString(),
                        dialogView.et_dialog_hours.text.toString(),
                        dialogView.et_dialog_minutes.text.toString()
                    )
                )


                editor.putString(
                    "DialogMoney${timeLineSize}",
                    dialogView.et_dialog_money.text.toString()
                ).apply()
                editor.putString(
                    "DialogContents${timeLineSize}",
                    dialogView.et_dialog_contents.text.toString()
                ).apply()
                editor.putString(
                    "DialogHours${timeLineSize}",
                    dialogView.et_dialog_hours.text.toString()
                ).apply()
                editor.putString(
                    "DialogMinutes${timeLineSize}",
                    dialogView.et_dialog_minutes.text.toString()
                ).apply()
                timeLineSize++

                editor.putInt("TimeLineSize", timeLineSize).apply()

            }
                .setNegativeButton("취소") { dialogInterface, i -> }
                .show()

        }

        //달력날짜를 클릭할때마다 tv_timeline_date 변경됨
        calender_view.setOnDateChangeListener { calendarView, year, month, day ->
            var date  = "${month+1}월 ${day}일"
            Log.d("TAG",date)

            tv_timeline_date.setText(date)

        }


        btn_toss_main.setOnClickListener {
            finish()
        }

        btn_toss_lookup.setOnClickListener {
            val intent = Intent(this, LookUpActivity::class.java)
            startActivityForResult(intent, LOOKUP_CODE)
            finish()
        }

        btn_toss_opened.setOnClickListener {
            val intent = Intent(this, OpenedActivity::class.java)
            startActivityForResult(intent, OPENED_CODE)
            finish()
        }

        btn_toss_allset.setOnClickListener {
            val intent = Intent(this, AllSetActivity::class.java)
            startActivityForResult(intent, AllSET_CODE)
            finish()
        }

        registerForContextMenu(rv_timeline_list)
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        //선택후에 바뀌는것

        when (item?.itemId) {
            R.id.revise_list -> { //수정

                //수정첫 처음 값을 변수에 저장
                val firstMoney: String = adapter.items[itemPosition].timeLineMoney
                val firstContents: String = adapter.items[itemPosition].timeLineContents
                val firstHour: String = adapter.items[itemPosition].timeLineHours
                val firstMinute: String = adapter.items[itemPosition].timeLineMinutes

                //수정클릭시에 나오는 dialog 화면
                val builder = AlertDialog.Builder(this)
                val dialogView = layoutInflater.inflate(R.layout.timeline_dialog_custom, null)
                val dialogMoney = dialogView.findViewById<EditText>(R.id.et_dialog_money)
                val dialogContents = dialogView.findViewById<EditText>(R.id.et_dialog_contents)
                val dialogHours = dialogView.findViewById<EditText>(R.id.et_dialog_hours)
                val dialogMinutes = dialogView.findViewById<EditText>(R.id.et_dialog_minutes)

                //수정화면에서 수정전의 값을 출력
                dialogMoney.setText(firstMoney)
                dialogContents.setText(firstContents)
                dialogHours.setText(firstHour)
                dialogMinutes.setText(firstMinute)


                builder.setView(dialogView).setPositiveButton("확인") { dialogInterface, i ->

                    //수정한 내용을 적용
                    adapter.items[itemPosition].timeLineMoney = dialogMoney.text.toString()
                    adapter.items[itemPosition].timeLineContents = dialogContents.text.toString()
                    adapter.items[itemPosition].timeLineHours = dialogHours.text.toString()
                    adapter.items[itemPosition].timeLineMinutes = dialogMinutes.text.toString()


                    //수정한 데이터를 SharedPreferences에 넣어준다
                    editor.putString(
                        "DialogMoney${itemPosition}",
                        adapter.items[itemPosition].timeLineMoney
                    )
                        .apply()
                    editor.putString(
                        "DialogContents${itemPosition}",
                        adapter.items[itemPosition].timeLineContents
                    )
                        .apply()
                    editor.putString(
                        "DialogHours${itemPosition}",
                        adapter.items[itemPosition].timeLineHours
                    )
                        .apply()
                    editor.putString(
                        "DialogMinutes${itemPosition}",
                        adapter.items[itemPosition].timeLineMinutes
                    )
                        .apply()

                    //화면 새로고침
                    adapter.notifyDataSetChanged()

                }
                    .setNegativeButton("취소") { dialogInterface, i -> }
                    .show()
            }
            R.id.delete_list -> { //삭제
                //SharedPreferences에 있는 timelinesize 수정

                for (i in itemPosition..timeLineSize-1) {
                    //SharedPreferences에서 데이터 삭제
                    editor.remove("DialogMoney${i}").apply()
                    editor.remove("DialogContents${i}").apply()
                    editor.remove("DialogHours${i}").apply()
                    editor.remove("DialogMinutes${i}").apply()

                    if (i + 1 <= timeLineSize-1) {
                        //삭제한 데이터 바로 다음에 있는 데이터를 삭제한데이터 자리에 넣어줌
                        editor.putString(
                            "DialogMoney${i}",
                            adapter.items[i+1].timeLineMoney
                        )
                            .apply()
                        editor.putString(
                            "DialogContents${i}",
                            adapter.items[i+1].timeLineContents
                        )
                            .apply()
                        editor.putString(
                            "DialogHours${i}",
                            adapter.items[i+1].timeLineHours
                        )
                            .apply()
                        editor.putString(
                            "DialogMinutes${i}",
                            adapter.items[i+1].timeLineMinutes
                        )
                            .apply()
                    }
                }

                adapter.deleteItems(itemPosition)
                timeLineSize--
                editor.putInt("TimeLineSize", timeLineSize).apply()

            }
        }
        return super.onContextItemSelected(item)
    }

}
