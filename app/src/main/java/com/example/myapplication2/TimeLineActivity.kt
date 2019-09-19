package com.example.myapplication2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import kotlinx.android.synthetic.main.toss_main_2.*
import kotlinx.android.synthetic.main.toss_main_2.btn_toss_allset
import kotlinx.android.synthetic.main.toss_main_2.btn_toss_lookup
import kotlinx.android.synthetic.main.toss_main_2.btn_toss_main
import kotlinx.android.synthetic.main.toss_main_2.btn_toss_opened

class TimeLineActivity : AppCompatActivity() {

    val LOOKUP_CODE = 222       //조회
    val OPENED_CODE = 444       //개설
    val AllSET_CODE = 555       //전체설정
    val NOTICE_CODE = 888       //알림

    var itemPosition = 0 // 해당아이템 위치

    private val adapter by lazy {
        TimeLineAdapter()
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
                val child = rv_timeline_list.findChildViewUnder(e.x, e.y)
                itemPosition = rv_timeline_list.getChildAdapterPosition(child!!)

                Log.d("TAG", "position ==>" + itemPosition)
                Toast.makeText(application, itemPosition.toString() + "번째 item", Toast.LENGTH_SHORT)
                    .show()


                return false
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
            }

        })



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
        //item.itemId

        when (item?.itemId) {
            R.id.revise_list -> {

                //처음 값 저장
                val firstMoney: String = adapter.items[itemPosition].timeLineMoney
                val firstContents: String = adapter.items[itemPosition].timeLineContents
                val firstHour: String = adapter.items[itemPosition].timeLineHours
                val firstMinute: String = adapter.items[itemPosition].timeLineMinutes

                val builder = AlertDialog.Builder(this)
                val dialogView = layoutInflater.inflate(R.layout.timeline_dialog_custom, null)
                val dialogMoney = dialogView.findViewById<EditText>(R.id.et_dialog_money)
                val dialogContents = dialogView.findViewById<EditText>(R.id.et_dialog_contents)
                val dialogHours = dialogView.findViewById<EditText>(R.id.et_dialog_hours)
                val dialogMinutes = dialogView.findViewById<EditText>(R.id.et_dialog_minutes)

                dialogMoney.setText(firstMoney)
                dialogContents.setText(firstContents)
                dialogHours.setText(firstHour)
                dialogMinutes.setText(firstMinute)


                builder.setView(dialogView).setPositiveButton("확인") { dialogInterface, i ->

                    adapter.items[itemPosition].timeLineMoney = dialogMoney.text.toString()
                    adapter.items[itemPosition].timeLineContents = dialogContents.text.toString()
                    adapter.items[itemPosition].timeLineHours = dialogHours.text.toString()
                    adapter.items[itemPosition].timeLineMinutes = dialogMinutes.text.toString()
                    adapter.notifyDataSetChanged()

                }
                    .setNegativeButton("취소") { dialogInterface, i -> }
                    .show()
            }
            R.id.delete_list -> {
                adapter.deleteItems(itemPosition)
            }
        }
        return super.onContextItemSelected(item)
    }

    public override fun onStart() {
        super.onStart()
        Log.i("TAG", "onStart")
    }

    public override fun onResume() {
        super.onResume()
        Log.i("TAG", "onResume")
    }

    public override fun onPause() {
        super.onPause()
        Log.i("TAG", "onPause")
    }

    public override fun onRestart() {
        super.onRestart()
        Log.i("TAG", "onRestart")
    }

    public override fun onStop() {
        super.onStop()
        Log.i("TAG", "onStop")
    }

    public override fun onDestroy() {
        super.onDestroy()
        Log.i("TAG", "onDestroy")
    }
}
