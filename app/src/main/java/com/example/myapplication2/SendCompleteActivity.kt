package com.example.myapplication2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_remittance_complete.*
import kotlinx.android.synthetic.main.toss_sending.*

class SendCompleteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remittance_complete)

        btn_back_to_send.setOnClickListener{ //뒤로가기
            val intent = Intent(this,SendingActivity::class.java)
            setResult(Activity.RESULT_OK,intent)
            finish()

        }

        var imgReceiver : Int = 0
        var moneyReceiver : String = ""
        var transferMoney : String = ""
        var bank : String = ""

        if (intent.hasExtra("imgReceiver")) { //송금받는사람 이미지 받아오기

            imgReceiver = intent.getIntExtra("imgReceiver",R.drawable.toss)
            img_send_complete.setImageResource(imgReceiver)
        }

        if (intent.hasExtra("moneyReceiver")) { //송금받는사람 이름 받아오기

            moneyReceiver = intent.getStringExtra("moneyReceiver").toString()
            tv_send_complete_name.setText(moneyReceiver)
        }

        if (intent.hasExtra("transferMoney")) { //송금금액 받아오기

            transferMoney = intent.getStringExtra("transferMoney").toString()
            tv_send_complete_money.setText(transferMoney)
        }

        if(intent.hasExtra("bank")) { //은행
            bank = intent.getStringExtra("bank").toString()
            tv_send_complete_bank.setText(bank)


        }

        btn_go_to_main.setOnClickListener {
           // finish()

           //모두 종료
            ActivityCompat.finishAffinity(this)
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }
}