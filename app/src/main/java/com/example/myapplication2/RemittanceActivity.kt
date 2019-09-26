package com.example.myapplication2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import kotlinx.android.synthetic.main.activity_remittance.*
import kotlinx.android.synthetic.main.activity_time_line.*
import org.w3c.dom.Text
import java.lang.StringBuilder

class RemittanceActivity : AppCompatActivity() {

    val REMITTANCE_CODE = 1010 //송금창
    val SENDING_CODE = 1234 // 돈보낼때
    var accountListIsOpen = false //내 계좌목록이 열려있나

    var itemPosition = 0 // 해당아이템 위치
    var contactItemPosition = 0; //연락처 아이템 위치

    lateinit var transferMoney: String
    var suggestion = arrayOf("박인서", "박철수", "이태원", "김철수")

    private val adapterList by lazy {
        RemittanceAdapter()
    }

    private val contactAdapter by lazy {
        ContactAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remittance)
        Log.i("TAG", "onCreate_Remittance")

        var adapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, suggestion) //ArrayAdapter 초기화
        act_search.threshold = 0
        act_search.setAdapter(adapter) //연결하여 채움

        if (intent.hasExtra("transferMoney")) { //송금금액 받아오기

            transferMoney = intent.getStringExtra("transferMoney").toString()
            tv_transfermoney.setText(transferMoney)
        }

        btn_back_to_main.setOnClickListener {
            //뒤로가기
            val intent = Intent(this, MainActivity::class.java)
            setResult(Activity.RESULT_OK, intent)
            finish()

        }

        act_search.setOnEditorActionListener { v, keyCode, event ->

            if (keyCode == EditorInfo.IME_ACTION_DONE) { //완료의 의미
                doSomething()
                true
            } else {
                false
            }
        }

        //계좌목록 recyclerView 로 구현
        rv_rem_list.adapter = adapterList
        rv_rem_list.layoutManager = LinearLayoutManager(this)
        rv_rem_list.hasFixedSize()

        rv_rem_list.visibility = View.GONE

        //연락처 목록 recyclerview
        rv_contact_list.adapter = contactAdapter
        rv_contact_list.layoutManager = LinearLayoutManager(this)
        rv_contact_list.hasFixedSize()


        //계좌목록 터치시 내 계좌목록 출력
        my_account_list.setOnClickListener {
            if (accountListIsOpen == false) { //열림
                accountListIsOpen = true
                rv_rem_list.visibility = View.VISIBLE
            } else { //닫힘
                accountListIsOpen = false
                rv_rem_list.visibility = View.GONE
            }

        }

        rv_rem_list.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
            }

            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                if (e.action == MotionEvent.ACTION_DOWN) { //손가락을 땔때 이벤트 발생
                    val child = rv_rem_list.findChildViewUnder(e.x, e.y)
                    itemPosition = rv_rem_list.getChildAdapterPosition(child!!)

                    touchSend()
                }
                return false
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
            }

        })

        rv_contact_list.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
            }

            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                if (e.action == MotionEvent.ACTION_DOWN) { //손가락을 땔때 이벤트 발생
                    val child = rv_contact_list.findChildViewUnder(e.x, e.y)
                    contactItemPosition = rv_contact_list.getChildAdapterPosition(child!!)

                    //checkUp()
                }
                return false
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
            }

        })


    }

    private fun checkUp() { //체크박스에 체크한 아이템을 맨 위로 올리기

        if (contactAdapter.contactItems[contactItemPosition].contactFavorite == true) {

            contactAdapter.notifyItemInserted(0)
            contactAdapter.notifyDataSetChanged()

        }

    }

    private fun touchSend() { //터치한 아이템에게 송금하기
        val intent = Intent(this, SendingActivity::class.java)
        intent.putExtra("imgReceiver", adapterList.items[itemPosition].remImgSrc)
        intent.putExtra("moneyReceiver", adapterList.items[itemPosition].remName)
        intent.putExtra("bank", adapterList.items[itemPosition].remNum)
        intent.putExtra("transferMoney", tv_transfermoney.text.toString())
        startActivityForResult(intent, SENDING_CODE)

    }

    private fun doSomething() {
        val intent = Intent(this, SendingActivity::class.java)

        for (i in suggestion) {
            if (i == act_search.text.toString()) {
                intent.putExtra("moneyReceiver", act_search.text.toString())
                intent.putExtra("transferMoney", tv_transfermoney.text.toString())
                startActivityForResult(intent, REMITTANCE_CODE)
            }
        }
    }


}
