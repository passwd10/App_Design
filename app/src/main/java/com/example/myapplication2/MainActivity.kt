package com.example.myapplication2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.toss_main_2.*
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.annotation.IntegerRes
import androidx.appcompat.app.AlertDialog
import com.facebook.stetho.inspector.helper.IntegerFormatter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.model.value.IntegerValue


class MainActivity : AppCompatActivity() {

    val MAIN_CODE = 111         //메인(송금)
    val LOOKUP_CODE = 222       //조회
    val TIMELINE_CODE = 333     //타임라인
    val OPENED_CODE = 444       //개설
    val AllSET_CODE = 555       //전체설정
    val SEND_CODE = 666         //보내기
    val DUTCHPAY_CODE = 777     //더치페이
    val NOTICE_CODE = 888       //알림

    val SENDING_CODE = 1234 // 돈보낼때


    lateinit var receiveBank: String   //받을 은행
    lateinit var receiveAccount: String //받을 계좌

    lateinit var mHandler: Handler
    lateinit var mRunnable: Runnable
    // Write a message to the database

    private lateinit var database: DatabaseReference// 데이터베이스에서 데이터를 읽거나 쓰기 위한 DatabaseReference의 객체
    private var inputCnt = 0    // 버튼 누른 횟수

    lateinit var resultView : ArrayList<TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("TAG", "onCreate")
        setContentView(R.layout.toss_main_2)

        resultView = arrayListOf<TextView>(
            findViewById(R.id.tv_result_window1),
            findViewById(R.id.tv_result_window2),
            findViewById(R.id.tv_result_window3),
            findViewById(R.id.tv_result_window4),
            findViewById(R.id.tv_result_window5),
            findViewById(R.id.tv_result_window6),
            findViewById(R.id.tv_result_window7)
        )

        val db = FirebaseFirestore.getInstance()

        val btn_notice: ImageButton = findViewById(R.id.btn_notice)
        var inputMoney: Int = 0  // 송금금액
        val add_number = AnimationUtils.loadAnimation(this, R.anim.num_translate)    //숫자추가 애니매이션
        add_number.duration = 300

        toss_send.visibility = View.GONE
        btn_delete_account.visibility = View.INVISIBLE //계좌 삭제 버튼
        MyFirebaseMessagingService()

        val clear_number =
            AnimationUtils.loadAnimation(this, R.anim.delete_num_translate) //애니매이션 삭제
        clear_number.duration = 300

        // 푸시알람 토큰 알아보기
//        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener(OnCompleteListener { task ->
//            if (!task.isSuccessful) {
//                Log.w("TAG", "getInstanceId failed", task.exception)
//                return@OnCompleteListener
//            }
//
//            // Get new Instance ID token
//            val token = task.result?.token
//
//            // Log and toast
//            val msg = getString(R.string.msg_token_fmt, token)
//            Log.d("Token", msg)
//            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
//
//        })

        btn0.setOnClickListener {
            if (inputCnt == 0) {

            } else {
                inputText(inputCnt, "0")
            }
        }

        btn1.setOnClickListener {
            inputText(inputCnt, "1")
        }

        btn2.setOnClickListener {
            inputText(inputCnt, "2")
        }

        btn3.setOnClickListener {
            inputText(inputCnt, "3")
        }

        btn4.setOnClickListener {
            inputText(inputCnt, "4")
        }

        btn5.setOnClickListener {
            inputText(inputCnt, "5")
        }

        btn6.setOnClickListener {
            inputText(inputCnt, "6")
        }

        btn7.setOnClickListener {
            inputText(inputCnt, "7")
        }

        btn8.setOnClickListener {
            inputText(inputCnt, "8")
        }

        btn9.setOnClickListener {
            inputText(inputCnt, "9")
        }

        btn_one_clear.setOnClickListener {
            deleteText(inputCnt)
        }

        btn_all_clear.setOnClickListener {
            inputCnt = 0

            for (i in 1 until 7) {
                resultView.get(i).text = "0"
                resultView.get(i).visibility = View.GONE
            }
            resultView.get(0).text = "0"
            resultView.get(0).startAnimation(add_number)

            text_warning.visibility = View.INVISIBLE

            btn_all_clear.visibility = View.INVISIBLE
            btn_one_clear.visibility = View.INVISIBLE

            toss_send.visibility = View.GONE
            toss_bottom_menu.visibility = View.VISIBLE
        }


        btn_toss_lookup.setOnClickListener {
            val intent = Intent(this, LookUpActivity::class.java)
            startActivityForResult(intent, LOOKUP_CODE)
        }

        btn_toss_timeline.setOnClickListener {
            val intent = Intent(this, TimeLineActivity2::class.java)
            startActivityForResult(intent, TIMELINE_CODE)
        }

        btn_toss_opened.setOnClickListener {
            val intent = Intent(this, OpenedActivity::class.java)
            startActivityForResult(intent, OPENED_CODE)
        }

        btn_toss_allset.setOnClickListener {
            val intent = Intent(this, AllSetActivity::class.java)
            startActivityForResult(intent, AllSET_CODE)
        }


        btn_send.setOnClickListener {

            inputMoney =
            pow(inputCnt) * tv_result_window1.text.get(0).toInt()
            + pow(inputCnt-1)* tv_result_window2.text.get(0).toInt()

            val accountText: String? = tv_main_bank.text.toString()
            if (accountText == "") {
                val intent = Intent(this, RemittanceActivity::class.java)
                intent.putExtra("transferMoney", tv_result_window1.text.toString())
                startActivityForResult(intent, SEND_CODE)
            } else {
                val intent = Intent(this, SendingActivity::class.java)
                intent.putExtra("transferMoney", tv_result_window1.text.toString()) //송금금액
                intent.putExtra("moneyReceiver", receiveBank) //받는 은행
                intent.putExtra("bank", receiveAccount) //받는 계좌
                startActivityForResult(intent, SENDING_CODE)

            }
        }

        btn_dutch_pay.setOnClickListener {
            val intent = Intent(this, DutchPayActivity::class.java)
            intent.putExtra("transferMoney", tv_result_window1.text.toString())
            startActivityForResult(intent, DUTCHPAY_CODE)
        }

        btn_notice.setOnClickListener {
            val intent = Intent(this, NoticeActivity::class.java)
            startActivityForResult(intent, NOTICE_CODE)
        }

        //카메라 버튼
        btn_camera.setOnClickListener {
            IntentIntegrator(this).initiateScan()
        }

        btn_delete_account.setOnClickListener {
            tv_main_bank.setText("") //계좌삭제
            btn_delete_account.visibility = View.INVISIBLE //버튼 숨김
        }
    }

    private fun pow(number: Int): Int {
        if (number == 1) {
            return 1
        } else if (inputCnt == 2) {
            return 10
        } else if (inputCnt == 3) {
            return 100
        } else if (inputCnt == 4) {
            return 1000
        } else if (inputCnt == 5) {
            return 10000
        } else if (inputCnt == 6) {
            return 100000
        } else if (inputCnt == 7) {
            return 1000000
        } else {
            return 1
        }
    }

    private fun deleteText(inputCnt: Int) {
        val clear_number =
            AnimationUtils.loadAnimation(this, R.anim.delete_num_translate) //애니매이션 삭제
        clear_number.duration = 300

        if (inputCnt == 1) {
            tv_result_window1.startAnimation(clear_number)
            tv_result_window1.text = "0"
            this.inputCnt = 0

            btn_all_clear.visibility = View.INVISIBLE
            btn_one_clear.visibility = View.INVISIBLE
            toss_send.visibility = View.GONE
            toss_bottom_menu.visibility = View.VISIBLE
        } else if (inputCnt > 1 && inputCnt < 7) {
            resultView.get(inputCnt - 1).startAnimation(clear_number)
            resultView.get(inputCnt - 1).visibility = View.GONE
            resultView.get(inputCnt - 1).text = "0"
            this.inputCnt--
        } else if (inputCnt == 7) {
            tv_result_window7.startAnimation(clear_number)
            tv_result_window7.visibility = View.GONE
            tv_result_window7.text = "0"
            this.inputCnt--
            text_warning.visibility = View.INVISIBLE
        }

    }

    private fun inputText(inputCnt: Int, inputNumber: String) {
        val add_number = AnimationUtils.loadAnimation(this, R.anim.num_translate)    //숫자추가 애니매이션
        add_number.duration = 300

        if (inputCnt == 0) {
            tv_result_window1.text = inputNumber
            tv_result_window1.startAnimation(add_number)
            this.inputCnt++
        } else if (inputCnt > 0 && inputCnt < 6) {

            resultView.get(inputCnt).text = inputNumber
            resultView.get(inputCnt).startAnimation(add_number)
            resultView.get(inputCnt).visibility = View.VISIBLE
            this.inputCnt++

        } else if (inputCnt == 6) {
            tv_result_window7.text = inputNumber
            tv_result_window7.startAnimation(add_number)
            tv_result_window7.visibility = View.VISIBLE
            this.inputCnt = 7

            if (tv_result_window1.text.toString().equals("1")) {
                //괜찮음
            } else {
                text_warning.visibility = View.VISIBLE
                for (i in 0 until 7) {
                    resultView.get(i).text = "0"
                }
                tv_result_window1.text = "2"
            }
        }

        btn_all_clear.visibility = View.VISIBLE
        btn_one_clear.visibility = View.VISIBLE

        toss_send.visibility = View.VISIBLE
        toss_bottom_menu.visibility = View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                mHandler = Handler()
                //Dialog 띄우기
                val builder = AlertDialog.Builder(this)
                val dialogBar: View = layoutInflater.inflate(R.layout.progress_bar, null)
                builder.setView(dialogBar)
                val alertDialog = builder.create()
                alertDialog.show()

                mRunnable = Runnable {
                    alertDialog.dismiss() //dialog창 종료
                    btn_delete_account.visibility = View.VISIBLE //삭제버튼 출력
                    receiveBank = result.contents.substring(0, 6) //받을 은행
                    receiveAccount = result.contents //계좌 전체
                    tv_main_bank.setText(receiveAccount)
                }

                mHandler.postDelayed(
                    mRunnable
                    , 3000
                )
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    var firstTime: Long = 0
    var secondTime: Long = 0

    override fun onBackPressed() { //뒤로가기

        secondTime = System.currentTimeMillis()
        if (secondTime - firstTime < 2000) {
            super.onBackPressed()
            finish()
//            android.os.Process.killProcess(android.os.Process.myPid())
        } else Toast.makeText(this, "뒤로가기 버튼을 한 번 더 누르시면 종료", Toast.LENGTH_SHORT).show()
        firstTime = System.currentTimeMillis()
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
