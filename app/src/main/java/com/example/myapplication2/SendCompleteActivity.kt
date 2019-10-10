package com.example.myapplication2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_remittance_complete.*
import kotlinx.android.synthetic.main.toss_sending.*

class SendCompleteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remittance_complete)

        var imgReceiver : Int = 0
        var moneyReceiver : String = ""
        var transferMoney : String = ""
        var bank : String = ""
        var tossAccountBalance = "" //토스계좌잔액

        val db = FirebaseFirestore.getInstance()

        db.collection("users").get().addOnSuccessListener { result ->
            for (document in result) {
                if (FirebaseAuth.getInstance().uid == document.id) {
                    tossAccountBalance = document.data.get("토스계좌잔액").toString()
                    tv_toss_account_balance.setText(tossAccountBalance)
                }
                Log.d("TAGGG", "${document.id} => ${document.data.get("토스계좌잔액")}")
            }

        }.addOnFailureListener { exception ->
            Log.w("TAGGG", "Error getting documents.", exception)
        }

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