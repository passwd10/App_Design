package com.example.myapplication2

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_my_profile.*
import java.lang.Exception


class MyProfileActivity : AppCompatActivity() {

    private val SELECT_DIALOG = 1919
    private val PICK_FROM_ALBUM = 33 // 앨범에서 사진을 선택


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)


        btn_my_info_back.setOnClickListener {
            finish()
        }

//        circleImageView_my_info_img.setOnClickListener {    //프로필사진 클릭시 다이얼로그 Activity로 연결
//
//            val intent = Intent(this,MyProfileImgDialog::class.java)
//            startActivityForResult(intent, SELECT_DIALOG)
//        }

        circleImageView_my_info_img.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            //intent.setType(MediaStore.Images.Media.CONTENT_TYPE)
            startActivityForResult(intent, PICK_FROM_ALBUM)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_FROM_ALBUM) {
            if(resultCode == Activity.RESULT_OK){
                try {
                    val inputStream = contentResolver.openInputStream(data?.data!!)
                    val imgBitmap = BitmapFactory.decodeStream(inputStream)
                    inputStream?.close()

                    val asyncTask = MyAsyncTask(circleImageView_my_info_img,imgBitmap)
                    asyncTask.execute()

                }catch (e : Exception){

                }
            } else {
                Toast.makeText(this,"사진선택취소",Toast.LENGTH_SHORT).show()
            }
        }

    }

}