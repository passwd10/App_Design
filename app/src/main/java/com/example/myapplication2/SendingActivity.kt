package com.example.myapplication2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_remittance.*
import kotlinx.android.synthetic.main.toss_sending.*

class SendingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.toss_sending)

        btn_back_to_rem.setOnClickListener{ //뒤로가기
            val intent = Intent(this,RemittanceActivity::class.java)
            setResult(Activity.RESULT_OK,intent)
            finish()

        }

        var moneyReceiver : String
        var transferMoney : String

        if (intent.hasExtra("moneyReceiver")) { //송금받는사람 이름 받아오기

            moneyReceiver = intent.getStringExtra("moneyReceiver").toString()
            tv_receiver.setText(moneyReceiver)
        }

        if (intent.hasExtra("transferMoney")) { //송금금액 받아오기

            transferMoney = intent.getStringExtra("transferMoney").toString()
            tv_receive_money.setText(transferMoney)
        }
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
