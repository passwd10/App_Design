package com.example.myapplication2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import kotlinx.android.synthetic.main.activity_remittance.*
import org.w3c.dom.Text
import java.lang.StringBuilder

class RemittanceActivity : AppCompatActivity() {

    val REMITTANCE_CODE = 1010 //송금창
    var accountListIsOpen = false //내 계좌목록이 열려있나

    lateinit var transferMoney: String
    var suggestion = arrayOf("박인서", "박철수", "이태원", "김철수")

    private val adapterList by lazy {
        RemittanceAdapter()
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

        adapterList.addItems(RemittanceItem(R.drawable.toss, "토스머니", "010-1234-1234"))
        adapterList.addItems(RemittanceItem(R.drawable.toss, "비상금", "토스 미션계좌"))
        adapterList.addItems(RemittanceItem(R.drawable.kbbank, "KB국민은행 계좌", "KB국민 514121010101101"))

        rv_rem_list.visibility = View.GONE

        //계좌목록 터치시 내 계좌목록 출력
        var accountLayout = findViewById<LinearLayout>(R.id.my_account_list)

        accountLayout.setOnClickListener {
            if (accountListIsOpen == false) { //열림
                accountListIsOpen = true
                rv_rem_list.visibility = View.VISIBLE
            } else { //닫힘
                accountListIsOpen = false
                rv_rem_list.visibility = View.GONE
            }

        }
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


    public override fun onStart() {
        super.onStart()
        Log.i("TAG", "onStart_Remmittance")
    }

    public override fun onResume() {
        super.onResume()
        Log.i("TAG", "onResume_Remmittance")
    }

    public override fun onPause() {
        super.onPause()
        Log.i("TAG", "onPause_Remmittance")
    }

    public override fun onRestart() {
        super.onRestart()
        Log.i("TAG", "onRestart_Remmittance")
    }

    public override fun onStop() {
        super.onStop()
        Log.i("TAG", "onStop_Remmittance")
    }

    public override fun onDestroy() {
        super.onDestroy()
        Log.i("TAG", "onDestroy_Remmittance")
    }
}
