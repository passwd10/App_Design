package com.example.myapplication2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.toss_main_2.*
import kotlinx.android.synthetic.main.toss_sending.*
import android.os.Handler
import android.widget.*
import androidx.appcompat.app.AlertDialog


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("TAG", "onCreate")
        setContentView(R.layout.toss_main_2)

        var is_first_input = true;

        val btn_notice: ImageButton = findViewById(R.id.btn_notice)

        toss_send.visibility = View.GONE
        btn_delete_account.visibility = View.INVISIBLE //계좌 삭제 버튼

        btn0.setOnClickListener {
            if (is_first_input == true) {

            } else {
                tv_result_window.append("0")
            }
            toss_send.visibility = View.VISIBLE
            toss_bottom_menu.visibility = View.GONE
        }

        btn1.setOnClickListener {
            if (is_first_input == true) {
                tv_result_window.setText("1")
                is_first_input = false
            } else {
                tv_result_window.append("1")
            }
            toss_send.visibility = View.VISIBLE
            toss_bottom_menu.visibility = View.GONE
        }

        btn2.setOnClickListener {
            if (is_first_input == true) {
                tv_result_window.setText("2")
                is_first_input = false
            } else {
                tv_result_window.append("2")
            }
            toss_send.visibility = View.VISIBLE
            toss_bottom_menu.visibility = View.GONE

        }

        btn3.setOnClickListener {
            if (is_first_input == true) {
                tv_result_window.setText("3")
                is_first_input = false
            } else {
                tv_result_window.append("3")
            }
            toss_send.visibility = View.VISIBLE
            toss_bottom_menu.visibility = View.GONE
        }

        btn4.setOnClickListener {
            if (is_first_input == true) {
                tv_result_window.setText("4")
                is_first_input = false
            } else {
                tv_result_window.append("4")
            }
            toss_send.visibility = View.VISIBLE
            toss_bottom_menu.visibility = View.GONE
        }

        btn5.setOnClickListener {
            if (is_first_input == true) {
                tv_result_window.setText("5")
                is_first_input = false
            } else {
                tv_result_window.append("5")
            }
            toss_send.visibility = View.VISIBLE
            toss_bottom_menu.visibility = View.GONE
        }

        btn6.setOnClickListener {
            if (is_first_input == true) {
                tv_result_window.setText("6")
                is_first_input = false
            } else {
                tv_result_window.append("6")
            }
            toss_send.visibility = View.VISIBLE
            toss_bottom_menu.visibility = View.GONE
        }

        btn7.setOnClickListener {
            if (is_first_input == true) {
                tv_result_window.setText("7")
                is_first_input = false
            } else {
                tv_result_window.append("7")
            }
            toss_send.visibility = View.VISIBLE
            toss_bottom_menu.visibility = View.GONE
        }

        btn8.setOnClickListener {
            if (is_first_input == true) {
                tv_result_window.setText("8")
                is_first_input = false
            } else {
                tv_result_window.append("8")
            }
            toss_send.visibility = View.VISIBLE
            toss_bottom_menu.visibility = View.GONE
        }

        btn9.setOnClickListener {
            if (is_first_input == true) {
                tv_result_window.setText("9")
                is_first_input = false
            } else {
                tv_result_window.append("9")
            }
            toss_send.visibility = View.VISIBLE
            toss_bottom_menu.visibility = View.GONE
        }

        btn_one_clear.setOnClickListener {
            if (tv_result_window.length() == 1 && tv_result_window.text != "0") {
                tv_result_window.setText("0")
                is_first_input = true
                toss_send.visibility = View.GONE
                toss_bottom_menu.visibility = View.VISIBLE
            }

            if (tv_result_window.length() != 1) {
                tv_result_window.setText(
                    tv_result_window.text.substring(
                        0,
                        tv_result_window.text.length - 1
                    )
                )
            }

        }

        btn_all_clear.setOnClickListener {
            is_first_input = true
            tv_result_window.setText("0")
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
            val accountText: String? = tv_main_bank.text.toString()
            if (accountText == "") {
                val intent = Intent(this, RemittanceActivity::class.java)
                intent.putExtra("transferMoney", tv_result_window.text.toString())
                startActivityForResult(intent, SEND_CODE)
            } else {
                val intent = Intent(this, SendingActivity::class.java)
                intent.putExtra("transferMoney", tv_result_window.text.toString()) //송금금액
                intent.putExtra("moneyReceiver", receiveBank) //받는 은행
                intent.putExtra("bank", receiveAccount) //받는 계좌
                startActivityForResult(intent, SENDING_CODE)

            }
        }

        btn_dutch_pay.setOnClickListener {
            val intent = Intent(this, DutchPayActivity::class.java)
            intent.putExtra("transferMoney", tv_result_window.text.toString())
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                mHandler = Handler()
                //Dialog 띄우기
                val builder = AlertDialog.Builder(this)
                val dialogBar : View = layoutInflater.inflate(R.layout.progress_bar,null)
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
