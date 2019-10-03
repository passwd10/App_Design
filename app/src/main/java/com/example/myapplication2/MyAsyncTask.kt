package com.example.myapplication2

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.provider.MediaStore
import android.util.Base64
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import kotlinx.android.synthetic.main.activity_my_profile.*
import java.io.InputStream
import java.net.URL

class MyAsyncTask(imageView: ImageView) : AsyncTask<String, Int, Bitmap>() {
    //첫번째인자. doInBackground()함수 호출 시 전달되는 인자의 자료형과 매칭
    //두번째인자. Integer는 onProgressUpdate()함수 호출 시 전달되는 인자의 자료형과 매칭
    //세번쨰인자. onPostExecute()와 onCancelled()함수 인자의 자료형과 매칭

    val bmImg = imageView
    var decodeBitmap : Bitmap? = null
    var imageUri : Uri? = null

    override fun doInBackground(vararg args: String?): Bitmap? {  //Bitmap 방식

        val imageBitmap = args[0] //갤러리에서 가져온 사진의 Bitmap을 String으로 저장
        val decodedByteArray : ByteArray

        //editor.putString("ImgBitmap",bitMapToString(imgBitmap)).apply()
        try {
            decodedByteArray = Base64.decode(imageBitmap,Base64.NO_WRAP)
            decodeBitmap = BitmapFactory.decodeByteArray(decodedByteArray,0,decodedByteArray.size)

            publishProgress()  //onProgressUpdate를 호출하는 메소드
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        return decodeBitmap
    }

//    override fun doInBackground(vararg args: String?): Uri? {
//
//        val imgUriStr = args[0] //갤러리에서 가져온 사진의 Uri를 String으로 저장
//
//        try {
//            imageUri = imgUriStr?.toUri()
//            publishProgress()  //onProgressUpdate를 호출하는 메소드
//        } catch (e: InterruptedException) {
//            e.printStackTrace()
//        }
//
//        return imageUri
//    }

    //MainThread에서 실행되는 함수들
    override fun onProgressUpdate(vararg values: Int?) {
        //doInBackground()함수에서 publishProgress()함수를 호출하면 호출되는 함수로 작업 스레드를 실행하는 도중에 UI처리를 담당함
        //일반적으로 작업 진행 정도를 표시하는 용도로 사용됨
        bmImg.setImageBitmap(decodeBitmap)
        //bmImg.setImageURI(imageUri)

        super.onProgressUpdate(*values)
    }

    override fun onPostExecute(result: Bitmap?) {  //사용자 UI에 ProgressBar를 표시하여 본격적인 작업 스레드에 들아기기 전에 작업 진행줄을 표시하는 구현이 들어감
        super.onPostExecute(result)
    }

}