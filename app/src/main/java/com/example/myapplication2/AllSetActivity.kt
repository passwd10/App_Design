package com.example.myapplication2

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_all_set.*
import kotlinx.android.synthetic.main.activity_toss_lookup.*
import kotlinx.android.synthetic.main.activity_toss_lookup.btn_toss_allset
import kotlinx.android.synthetic.main.activity_toss_lookup.btn_toss_lookup
import kotlinx.android.synthetic.main.activity_toss_lookup.btn_toss_main
import kotlinx.android.synthetic.main.activity_toss_lookup.btn_toss_opened
import kotlinx.android.synthetic.main.activity_toss_lookup.btn_toss_timeline
import kotlinx.android.synthetic.main.toss_main_2.*

class AllSetActivity : AppCompatActivity() {

    val MAIN_CODE = 111         //메인(송금)
    val LOOKUP_CODE = 222       //조회
    val TIMELINE_CODE = 333     //타임라인
    val OPENED_CODE = 444       //개설
    val AllSET_CODE = 555       //전체설정
    val NOTICE_CODE = 888       //알림
    val HOMEPAGE_CODE = 999     //홈페이지
    val DIAL_CODE = 1111        //전화

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_set)


        btn_toss_main.setOnClickListener {
            finish()
        }

        btn_toss_lookup.setOnClickListener {
            val intent = Intent(this, LookUpActivity::class.java)
            startActivityForResult(intent,LOOKUP_CODE)
            finish()
        }

        btn_toss_timeline.setOnClickListener {
            val intent = Intent(this, TimeLineActivity2::class.java)
            startActivityForResult(intent, TIMELINE_CODE)
            finish()
        }

        btn_toss_opened.setOnClickListener {
            val intent = Intent(this, OpenedActivity::class.java)
            startActivityForResult(intent, OPENED_CODE)
            finish()
        }


        btn_toss_homepage.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://toss.im"))
            if (intent.resolveActivity(packageManager) != null) {
                startActivityForResult(intent, HOMEPAGE_CODE)
            }
        }

        btn_toss_call.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL,Uri.parse("tel:15994905"))
            if (intent.resolveActivity(packageManager) != null) {
                startActivityForResult(intent, DIAL_CODE)
            }
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
