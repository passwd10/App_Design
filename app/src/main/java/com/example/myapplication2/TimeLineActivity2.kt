package com.example.myapplication2

import android.content.Context
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
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_time_line.*
import kotlinx.android.synthetic.main.timeline_dialog_custom.*
import kotlinx.android.synthetic.main.timeline_dialog_custom.view.*
import kotlinx.android.synthetic.main.toss_main_2.*
import kotlinx.android.synthetic.main.toss_main_2.btn_toss_allset
import kotlinx.android.synthetic.main.toss_main_2.btn_toss_lookup
import kotlinx.android.synthetic.main.toss_main_2.btn_toss_main
import kotlinx.android.synthetic.main.toss_main_2.btn_toss_opened
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

class TimeLineActivity2 : AppCompatActivity() {

    val LOOKUP_CODE = 222       //조회
    val OPENED_CODE = 444       //개설
    val AllSET_CODE = 555       //전체설정
    val NOTICE_CODE = 888       //알림

    var itemPosition = 1// 해당아이템 위치

    lateinit var date: String //오늘이 몇월 몇일인지
    var theDateSize = 0 //날짜별 리스트의 사이즈

    private val adapter by lazy {
        TimeLineAdapter()
    }
    private val pref by lazy {
        this.getPreferences(Context.MODE_PRIVATE)
    }
    private val editor by lazy {
        pref.edit()
    }

    private var dataList: MutableList<String> = mutableListOf() //특정 날짜의 데이터를 모두 담고있는 배열

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_line)

        rv_timeline_list.adapter = adapter
        rv_timeline_list.layoutManager = LinearLayoutManager(this)
        rv_timeline_list.hasFixedSize()

        //현재날짜를 date 변수에 저장
        val currentTime = Calendar.getInstance().time
        val yearFormat = SimpleDateFormat("yyyy", Locale.getDefault())
        val monthFormat = SimpleDateFormat("MM", Locale.getDefault())
        val dayFormat = SimpleDateFormat("dd", Locale.getDefault())
        val year = yearFormat.format(currentTime)
        val month = monthFormat.format(currentTime)
        val day = dayFormat.format(currentTime)

        var timelineDate = "${month}월 ${day}일" //현재 날짜
        date = "${year}${month}${day}" //현재 날짜
        tv_timeline_date.setText(timelineDate) // 화면첫 실행시에 현재날짜를 넣어줌

        // recyclerview 터치 이벤트
        rv_timeline_list.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
            }

            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                if (e.action == MotionEvent.ACTION_DOWN) { //손가락을 땔때 이벤트 발생
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

        firstLoadData(date)

        //내역추가 버튼
        btn_add_timeline.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.timeline_dialog_custom, null)
            builder.setView(dialogView).setPositiveButton("확인") { dialogInterface, i ->
                saveData(dialogView)
            }
                .setNegativeButton("취소") { dialogInterface, i -> }
                .show()

        }

        //달력날짜를 클릭할때마다 tv_timeline_date 변경됨
        calender_view.setOnDateChangeListener { calendarView, year, month, day ->
            if (day < 10) {
                date = "${year}${month + 1}0${day}"
                timelineDate = "${month + 1}월 0${day}일" //현재 날짜
            } else {
                date = "${year}${month + 1}${day}"
                timelineDate = "${month + 1}월 ${day}일" //현재 날짜
            }
            Log.d("TAG", date)

            tv_timeline_date.setText(timelineDate)

            adapter.items.clear() //화면 초기화
            adapter.notifyDataSetChanged()
            dataList.clear() //저장할 배열 초기화

            val nowData: MutableList<String> = getStringMutablePref(date)
            if (nowData.size > 0) {
                // 해당날짜의 데이터사이즈
                loadData(nowData.size) //데이터 불러와서 화면에 출력
                theDateSize = nowData.size   //불러온 데이터사이즈를 넣어줌
            } else {
                theDateSize = 0
            }

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

    private fun firstLoadData(date: String) {   //첫화면 데이터 불러오기

        val nowData: MutableList<String> = getStringMutablePref(date)
        if (nowData.size > 0) {
            loadData(nowData.size) // 해당날짜의 데이터사이즈
        }
    }

    private fun loadData(nowDateSize: Int) { // SharedPreferences에서 데이터 불러와서 화면에 출력

        if (nowDateSize > 0) {

            for (i in 0 until nowDateSize) {
                dataList = getStringMutablePref(date) //mutable로 변경

                val splitData: List<String> = dataList[i].split(",", "[", "]")   // , 를 기준으로 자름
                adapter.addItems(   //화면에 출력

                    TimeLineItem(
                        R.drawable.question,
                        splitData[1],    // 돈
                        splitData[2],    // 내역
                        splitData[3],    // 시간
                        splitData[4]     // 분
                    )
                )
            }

        }

    }

    private fun saveData(dialogView: View) { //데이터 SharedPreferences에 저장

        val saveData: MutableList<String> = mutableListOf()

        saveData.add(dialogView.et_dialog_money.text.toString())     // 돈
        saveData.add(dialogView.et_dialog_contents.text.toString())  // 내역
        saveData.add(dialogView.et_dialog_hours.text.toString())     // 시간
        saveData.add(dialogView.et_dialog_minutes.text.toString())   // 분

        dataList.add(theDateSize, saveData.toString())

        theDateSize++ // 해당날짜의 데이터사이즈 증가

        setStringMutablePref(date, dataList)    // MutableList -> Json

        adapter.addItems(
            TimeLineItem(
                R.drawable.question,
                dialogView.et_dialog_money.text.toString(),
                dialogView.et_dialog_contents.text.toString(),
                dialogView.et_dialog_hours.text.toString(),
                dialogView.et_dialog_minutes.text.toString()
            )
        )
    }

    //Mutablelist를 JSON으로 변환하여 SharedPreferences에 String을 저장하는 함수
    private fun setStringMutablePref(key: String, valueList: MutableList<String>) {

        val jsonArray = JSONArray() // JsonArray 생성
        for (i in 0 until valueList.size) {
            jsonArray.put(valueList.get(i)) // Mutablelist를 jsonArray에 넣어줌
        }
        if (!valueList.isEmpty()) {
            editor.putString(key, jsonArray.toString()) // jsonArray 전체를 SharedPreferences에 넣어줌
        } else {
            editor.putString(key, null)
        }

        editor.apply()
    }

    //SharedPreferences에서 JSON형식의 String을 가져와서 MutableList로 변환하는 함수
    private fun getStringMutablePref(key: String): MutableList<String> {

        val timelineList: MutableList<String> = mutableListOf()
        val json = pref.getString(key, null) // ? 좀 이상한데

        if (json != null) {
            try {
                val jsonArray = JSONArray(json)         // jsonArray생성

                for (i in 0 until jsonArray.length()) {
                    //val url = jsonArray.optString(i) // jsonArray에서 String을 하나씩 저장
                    val url2 = jsonArray.optString(i)    // jsonArray2에서 Jsonarray을 하나씩 저장

//                    for (j in 0 until url2.length()) {
//                        val url = jsonArray.optString(j)    //jsonArray에서 String을 하나씩 저장
//                        timelineList[i].add(url)
//                    }

                    timelineList.add(url2)   //2차원배열 완성
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        return timelineList // Mutablelist로 바꾼 jsonArray를 반환

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

                //값 수정
                builder.setView(dialogView).setPositiveButton("확인") { dialogInterface, i ->

                    val reviseData: MutableList<String> = mutableListOf()

                    dataList.removeAt(itemPosition) //해당내역 삭제
                    //삭제한 자리에 추가

                    reviseData.add(dialogMoney.text.toString())     // 돈
                    reviseData.add(dialogContents.text.toString())  // 내역
                    reviseData.add(dialogHours.text.toString())     // 시간
                    reviseData.add(dialogMinutes.text.toString())   // 분

                    dataList.add(itemPosition,reviseData.toString())

                    //수정한 데이터를 SharedPreferences에 넣어준다
                    setStringMutablePref(date, dataList)

                    //수정한 내용을 화면에 적용
                    adapter.items[itemPosition].timeLineMoney = dialogMoney.text.toString()
                    adapter.items[itemPosition].timeLineContents = dialogContents.text.toString()
                    adapter.items[itemPosition].timeLineHours = dialogHours.text.toString()
                    adapter.items[itemPosition].timeLineMinutes = dialogMinutes.text.toString()

                    //화면 새로고침
                    adapter.notifyDataSetChanged()

                }
                    .setNegativeButton("취소") { dialogInterface, i -> }
                    .show()
            }
            R.id.delete_list -> { //삭제

                //SharedPreferences에서 데이터 삭제
                dataList.removeAt(itemPosition)     //배열에서 삭제
                adapter.deleteItems(itemPosition)   //화면에서 삭제
                setStringMutablePref(date,dataList) //SharedPreferences에 상태 저장

                theDateSize--

            }
        }
        return super.onContextItemSelected(item)
    }

}
