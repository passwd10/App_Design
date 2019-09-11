package com.example.myapplication2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.substring
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import kotlinx.android.synthetic.main.toss_main_2.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.toss_main_2)

        var is_first_input = true;

        val btn_notice : ImageButton = findViewById(R.id.btn_notice)
        val tv_result: TextView = findViewById(R.id.tv_result_window)
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

        btn_all_clear.setOnClickListener {
            is_first_input = true
            tv_result.setText("0")
            toss_send.visibility = View.GONE
            toss_bottom_menu.visibility = View.VISIBLE
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

        btn_toss_lookup.setOnClickListener {
            val intent = Intent(this, LookUpActivity::class.java)
            startActivity(intent)
        }

        btn_toss_allset.setOnClickListener {
            val intent = Intent(this, AllSetActivity::class.java)
            startActivity(intent)
        }

        btn_toss_main.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btn_toss_opened.setOnClickListener {
            val intent = Intent(this, OpenedActivity::class.java)
            startActivity(intent)
        }

        btn_toss_timeline.setOnClickListener {
            val intent = Intent(this, TimeLineActivity::class.java)
            startActivity(intent)
        }

        btn_send.setOnClickListener {
            val intent = Intent(this, RemittanceActivity::class.java)
            intent.putExtra("transferMoney", tv_result.text.toString())
            startActivity(intent)
        }

        btn_notice.setOnClickListener {
            val intent = Intent(this, NoticeActivity::class.java)
            startActivity(intent)
        }

    }
}
