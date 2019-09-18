package com.example.myapplication2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_remittance.*

class DutchPayActivity : AppCompatActivity() {

    lateinit var transferMoney: String
    var suggestion = arrayOf("박인서", "박철수", "이태원", "김철수")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dutch_pay)
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
            //startActivity(intent)

//            startActivityForResult(this,)
        }

        act_search.setOnEditorActionListener { v, keyCode, event ->

            if (keyCode == EditorInfo.IME_ACTION_DONE) { //완료의 의미
                doSomething()
                true
            } else {
                false
            }
        }
    }

    private fun doSomething() {
        val intent = Intent(this, SendingActivity::class.java)

        for (i in suggestion) {
            if (i == act_search.text.toString()) {
                intent.putExtra("moneyReceiver", act_search.text.toString())
                intent.putExtra("transferMoney", tv_transfermoney.text.toString())
                startActivity(intent)
            }
        }
    }
}
