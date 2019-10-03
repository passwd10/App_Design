package com.example.myapplication2

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_my_profile.*
import java.io.ByteArrayOutputStream
import java.lang.Exception


class MyProfileActivity : AppCompatActivity() {

    private val SELECT_DIALOG = 1919
    private val PICK_FROM_ALBUM = 33 // 앨범에서 사진을 선택
    private val pref by lazy {
        getPreferences(Context.MODE_PRIVATE)
    }
    private val editor by lazy {
        pref.edit()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        //String을 Bitmap으로 변환
        val decodedByteArray =
            Base64.decode(pref.getString("ImgBitmap", "").toString(), Base64.NO_WRAP)
        val decodeBitmap = BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.size)

        circleImageView_my_info_img.setImageBitmap(decodeBitmap)

        btn_my_info_back.setOnClickListener {
            finish()
        }

//        circleImageView_my_info_img.setOnClickListener {    //프로필사진 클릭시 다이얼로그 Activity로 연결
//
//            val intent = Intent(this,MyProfileImgDialog::class.java)
//            startActivityForResult(intent, SELECT_DIALOG)
//        }

        btn_my_info_revise.setOnClickListener {

        }
        circleImageView_my_info_img.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_FROM_ALBUM)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) { //Bitmap 방식
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_FROM_ALBUM) {
            if(resultCode == Activity.RESULT_OK){
                try {
                    val inputStream = contentResolver.openInputStream(data?.data!!)
                    val imgBitmap = BitmapFactory.decodeStream(inputStream)
                    inputStream?.close()

                    editor.putString("ImgBitmap",bitMapToString(imgBitmap)).apply()
                    val asyncTask = MyAsyncTask(circleImageView_my_info_img)
                    asyncTask.execute(bitMapToString(imgBitmap))

                }catch (e : Exception){

                }
            } else {
                Toast.makeText(this,"사진선택취소",Toast.LENGTH_SHORT).show()
            }
        }

    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) { //URI 방식
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == PICK_FROM_ALBUM) {
//            if (resultCode == Activity.RESULT_OK) {
//                val asyncTask = MyAsyncTask(circleImageView_my_info_img)
//                asyncTask.execute(data?.data.toString())
//                editor.putString("imgUri",data?.data.toString()).apply()
//            }
//        }
//    }

    private fun bitMapToString(bitmap: Bitmap): String? {

        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val imageBytes = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(imageBytes, Base64.NO_WRAP)
    }
}