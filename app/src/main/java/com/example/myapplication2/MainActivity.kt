package com.example.myapplication2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.toss_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.toss_main)

        var cnt = 1
        var result_num = findViewById(R.id.tv_result_window)
        val btn1:Button = findViewById(R.id.btn1)
        val btn2:Button = findViewById(R.id.btn2)


        btn1.setOnClickListener(View.OnClickListener {
            result_num = result_num+(1)
            tv_result_window.setText(result_num)
            cnt++
        })

        btn2.setOnClickListener(View.OnClickListener {
            result_num = result_num+(2)
            tv_result_window.setText(result_num)
            cnt++
        })
    }


}
