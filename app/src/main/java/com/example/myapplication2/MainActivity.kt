package com.example.myapplication2

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_camera.*
import kotlinx.android.synthetic.main.toss_main_2.*
import kotlinx.android.synthetic.main.toss_sending.*
import kotlinx.android.synthetic.main.toss_main_2.toss_send as toss_send1

class MainActivity : AppCompatActivity() {

    val MAIN_CODE = 111         //메인(송금)
    val LOOKUP_CODE = 222       //조회
    val TIMELINE_CODE = 333     //타임라인
    val OPENED_CODE = 444       //개설
    val AllSET_CODE = 555       //전체설정
    val SEND_CODE = 666         //보내기
    val DUTCHPAY_CODE = 777     //더치페이
    val NOTICE_CODE = 888       //알림

    lateinit var tv_result: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {

        } else {
            tv_result.setText(savedInstanceState.getString("saveNum"))
        }

        Log.i("TAG", "onCreate")
        setContentView(R.layout.toss_main_2)

        var is_first_input = true;
        tv_result = findViewById(R.id.tv_result_window)

        val btn_notice: ImageButton = findViewById(R.id.btn_notice)

        val btn0: Button = findViewById(R.id.btn0)
        val btn1: Button = findViewById(R.id.btn1)
        val btn2: Button = findViewById(R.id.btn2)
        val btn3: Button = findViewById(R.id.btn3)
        val btn4: Button = findViewById(R.id.btn4)
        val btn5: Button = findViewById(R.id.btn5)
        val btn6: Button = findViewById(R.id.btn6)
        val btn7: Button = findViewById(R.id.btn7)
        val btn8: Button = findViewById(R.id.btn8)
        val btn9: Button = findViewById(R.id.btn9)

        toss_send.visibility = View.GONE

        btn0.setOnClickListener {
            if (is_first_input == true) {

            } else {
                tv_result.append("0")
            }
            toss_send.visibility = View.VISIBLE
            toss_bottom_menu.visibility = View.GONE
        }

        btn1.setOnClickListener {
            if (is_first_input == true) {
                tv_result.setText("1")
                is_first_input = false
            } else {
                tv_result.append("1")
            }
            toss_send.visibility = View.VISIBLE
            toss_bottom_menu.visibility = View.GONE
        }

        btn2.setOnClickListener {
            if (is_first_input == true) {
                tv_result.setText("2")
                is_first_input = false
            } else {
                tv_result.append("2")
            }
            toss_send.visibility = View.VISIBLE
            toss_bottom_menu.visibility = View.GONE

        }

        btn3.setOnClickListener {
            if (is_first_input == true) {
                tv_result.setText("3")
                is_first_input = false
            } else {
                tv_result.append("3")
            }
            toss_send.visibility = View.VISIBLE
            toss_bottom_menu.visibility = View.GONE
        }

        btn4.setOnClickListener {
            if (is_first_input == true) {
                tv_result.setText("4")
                is_first_input = false
            } else {
                tv_result.append("4")
            }
            toss_send.visibility = View.VISIBLE
            toss_bottom_menu.visibility = View.GONE
        }

        btn5.setOnClickListener {
            if (is_first_input == true) {
                tv_result.setText("5")
                is_first_input = false
            } else {
                tv_result.append("5")
            }
            toss_send.visibility = View.VISIBLE
            toss_bottom_menu.visibility = View.GONE
        }

        btn6.setOnClickListener {
            if (is_first_input == true) {
                tv_result.setText("6")
                is_first_input = false
            } else {
                tv_result.append("6")
            }
            toss_send.visibility = View.VISIBLE
            toss_bottom_menu.visibility = View.GONE
        }

        btn7.setOnClickListener {
            if (is_first_input == true) {
                tv_result.setText("7")
                is_first_input = false
            } else {
                tv_result.append("7")
            }
            toss_send.visibility = View.VISIBLE
            toss_bottom_menu.visibility = View.GONE
        }

        btn8.setOnClickListener {
            if (is_first_input == true) {
                tv_result.setText("8")
                is_first_input = false
            } else {
                tv_result.append("8")
            }
            toss_send.visibility = View.VISIBLE
            toss_bottom_menu.visibility = View.GONE
        }

        btn9.setOnClickListener {
            if (is_first_input == true) {
                tv_result.setText("9")
                is_first_input = false
            } else {
                tv_result.append("9")
            }
            toss_send.visibility = View.VISIBLE
            toss_bottom_menu.visibility = View.GONE
        }

        btn_one_clear.setOnClickListener {
            if (tv_result.length() == 1 && tv_result.text != "0") {
                tv_result.setText("0")
                is_first_input = true
                toss_send.visibility = View.GONE
                toss_bottom_menu.visibility = View.VISIBLE
            }

            if (tv_result.length() != 1) {
                tv_result.setText(tv_result.text.substring(0, tv_result.text.length - 1))
            }

        }

        btn_all_clear.setOnClickListener {
            is_first_input = true
            tv_result.setText("0")
            toss_send.visibility = View.GONE
            toss_bottom_menu.visibility = View.VISIBLE
        }


        btn_toss_lookup.setOnClickListener {
            val intent = Intent(this, LookUpActivity::class.java)
            startActivityForResult(intent,LOOKUP_CODE)
        }

        btn_toss_timeline.setOnClickListener {
            val intent = Intent(this, TimeLineActivity::class.java)
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
            val intent = Intent(this, RemittanceActivity::class.java)
            intent.putExtra("transferMoney", tv_result.text.toString())
            startActivityForResult(intent, SEND_CODE)
        }

        btn_dutch_pay.setOnClickListener {
            val intent = Intent(this, DutchPayActivity::class.java)
            intent.putExtra("transferMoney", tv_result.text.toString())
            startActivityForResult(intent, DUTCHPAY_CODE)
        }

        btn_notice.setOnClickListener {
            val intent = Intent(this, NoticeActivity::class.java)
            startActivityForResult(intent, NOTICE_CODE)
        }

        //카메라 버튼
        btn_camera.setOnClickListener {
            var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent,123)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 123)
        {
            var bmp=data?.extras?.get("data") as Bitmap
            iv_cam.setImageBitmap(bmp)
        } else if(requestCode == 333) {

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

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putString("saveNum", tv_result.text.toString())
        //tv_result 값을 save_num 키에 저장함
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        tv_result.setText(savedInstanceState.getString("saveNum"))

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
