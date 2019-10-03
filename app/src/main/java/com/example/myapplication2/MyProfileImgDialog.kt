package com.example.myapplication2

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.navigation.ActivityNavigatorExtras
import kotlinx.android.synthetic.main.activity_my_profile.*
import kotlinx.android.synthetic.main.my_profile_img_dialog.*
import java.lang.Exception
import java.net.URI

class MyProfileImgDialog : AppCompatActivity(), View.OnClickListener {

    private val PICK_FROM_ALBUM = 33 // 앨범에서 사진을 선택
    private val TURN_ON_CAMERA = 30 // 카메라를 켬

    override fun onClick(p0: View?) {
        when (p0) {
            take_a_picture -> {
                dispatchCameraIntent()
            }
            take_img_album -> {
                albumIntent()
            }
        }
    }

    private fun dispatchCameraIntent() { //카메라 함수
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        finish()
        startActivityForResult(intent,TURN_ON_CAMERA)
    }

    private fun albumIntent() { // 앨범 함수
        val intent = Intent(Intent.ACTION_PICK)
        finish()
        intent.type = "image/*"
        //intent.setType(MediaStore.Images.Media.CONTENT_TYPE)
        startActivityForResult(intent, PICK_FROM_ALBUM)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_profile_img_dialog)

        take_a_picture.setOnClickListener(this)
        take_img_album.setOnClickListener(this)
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if(requestCode == PICK_FROM_ALBUM) {
//            if(resultCode == Activity.RESULT_OK){
//              //  circleImageView_my_info_img.setImageURI(data?.data)
//            } else {
//                Toast.makeText(this,"사진선택취소",Toast.LENGTH_SHORT).show()
//            }
//        }
//
//    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_FROM_ALBUM) {
            if(resultCode == Activity.RESULT_OK){
                try {
                    val inputStream = contentResolver.openInputStream(data?.data!!)
                    val imgBitmap = BitmapFactory.decodeStream(inputStream)
                    inputStream?.close()

                    circleImageView_my_info_img.setImageBitmap(imgBitmap)

                }catch (e : Exception){

                }
            } else {
                Toast.makeText(this,"사진선택취소",Toast.LENGTH_SHORT).show()
            }
        }

    }
}