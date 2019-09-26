package com.example.myapplication2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_remittance.*
import kotlinx.android.synthetic.main.toss_sending.*

class SendingActivity : AppCompatActivity() {

    val SENDING_COMPLETE = 9999 // 돈보내기완료

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.toss_sending)

        btn_back_to_rem.setOnClickListener{ //뒤로가기
            val intent = Intent(this,RemittanceActivity::class.java)
            setResult(Activity.RESULT_OK,intent)
            finish()

        }

        var imgReceiver : Int = 0
        var moneyReceiver : String = ""
        var transferMoney : String = ""
        var bank : String = ""

        if (intent.hasExtra("imgReceiver")) { //송금받는사람 이미지 받아오기

            imgReceiver = intent.getIntExtra("imgReceiver",R.drawable.toss)
            iv_send_img.setImageResource(imgReceiver)
        }

        if (intent.hasExtra("moneyReceiver")) { //송금받는사람 이름 받아오기

            moneyReceiver = intent.getStringExtra("moneyReceiver").toString()
            tv_receiver.setText(moneyReceiver)
        }

        if (intent.hasExtra("transferMoney")) { //송금금액 받아오기

            transferMoney = intent.getStringExtra("transferMoney").toString()
            tv_receive_money.setText(transferMoney)
        }

        if(intent.hasExtra("bank")) { //은행
            bank = intent.getStringExtra("bank").toString()
            tv_receive_bank.setText(bank)

        }

        btn_send_complete.setOnClickListener {
            val intent = Intent(this, SendCompleteActivity::class.java)
            intent.putExtra("imgReceiver", imgReceiver)
            intent.putExtra("moneyReceiver", moneyReceiver)
            intent.putExtra("bank", bank)
            intent.putExtra("transferMoney", transferMoney)
            startActivityForResult(intent, SENDING_COMPLETE)
        }
    }


}
