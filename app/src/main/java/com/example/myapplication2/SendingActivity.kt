package com.example.myapplication2

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.core.os.postDelayed
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_remittance.*
import kotlinx.android.synthetic.main.progress_bar.*
import kotlinx.android.synthetic.main.progress_bar.view.*
import kotlinx.android.synthetic.main.toss_sending.*

class SendingActivity : AppCompatActivity() {

    val SENDING_COMPLETE = 9999 // 돈보내기완료

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.toss_sending)

        btn_back_to_rem.setOnClickListener {
            //뒤로가기
            finish()
        }

        var imgReceiver: Int = 0
        var moneyReceiver: String = ""
        var transferMoney: String = ""
        var bank: String = ""
        var tossAccountBalance = "" //토스계좌잔액
        var tossAccountInt = 0
        var transferMoneyInt = 0

        val db = FirebaseFirestore.getInstance()

        db.collection("users").get().addOnSuccessListener { result ->
            for (document in result) {
                if (FirebaseAuth.getInstance().uid == document.id) {
                    tossAccountBalance = document.data.get("토스계좌잔액").toString()
                    toss_money.setText(tossAccountBalance)
                    tossAccountInt = tossAccountBalance.toInt()
                    transferMoneyInt = transferMoney.toInt()
                    tossAccountInt -= transferMoneyInt
                }
                Log.d("TAGGG", "${document.id} => ${document.data.get("토스계좌잔액")}")
            }

        }.addOnFailureListener { exception ->
            Log.w("TAGGG", "Error getting documents.", exception)
        }


        if (intent.hasExtra("imgReceiver")) { //송금받는사람 이미지 받아오기

            imgReceiver = intent.getIntExtra("imgReceiver", R.drawable.toss)
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

        if (intent.hasExtra("bank")) { //은행
            bank = intent.getStringExtra("bank").toString()
            tv_receive_bank.setText(bank)
        }
        
        btn_send_complete.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            val dialogBar: View = layoutInflater.inflate(R.layout.progress_bar, null)
            dialogBar.tv_progress_bar.setText("송금중")
            builder.setView(dialogBar)
            val alertDialog = builder.create()
            alertDialog.show()

            val shandler = Handler()
            val sRunnable = Runnable {
                alertDialog.dismiss()

                val intent = Intent(this, SendCompleteActivity::class.java)
                intent.putExtra("imgReceiver", imgReceiver)
                intent.putExtra("moneyReceiver", moneyReceiver)
                intent.putExtra("bank", bank)
                intent.putExtra("transferMoney", transferMoney)
                startActivityForResult(intent, SENDING_COMPLETE)
            }
            shandler.postDelayed(sRunnable, 1500)

            val db2 = FirebaseFirestore.getInstance()

            db2.collection("users").document(FirebaseAuth.getInstance().uid.toString())
                .update("토스계좌잔액", tossAccountInt)
                .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully updated!") }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)
                }
        }
    }


}
