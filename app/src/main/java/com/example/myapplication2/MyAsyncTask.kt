package com.example.myapplication2

import android.graphics.Bitmap
import android.os.AsyncTask
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_my_profile.*

class MyAsyncTask(imageView: ImageView, bitmap: Bitmap) : AsyncTask<String, Int, Boolean>() {
    //첫번째인자. doInBackground()함수 호출 시 전달되는 인자의 자료형과 매칭
    //두번째인자. Integer는 onProgressUpdate()함수 호출 시 전달되는 인자의 자료형과 매칭
    //세번쨰인자. onPostExecute()와 onCancelled()함수 인자의 자료형과 매칭

    val imgView: ImageView = imageView
    val bitMap = bitmap

    override fun doInBackground(vararg p0: String?): Boolean {

            try{
                Thread.sleep(2000)
                publishProgress()  //onProgressUpdate를 호출하는 메소드
            } catch (e : InterruptedException) {
                e.printStackTrace()
            }

        return true
    }

    //MainThread에서 실행되는 함수들
    override fun onProgressUpdate(vararg values: Int?) {
        //doInBackground()함수에서 publishProgress()함수를 호출하면 호출되는 함수로 작업 스레드를 실행하는 도중에 UI처리를 담당함
        //일반적으로 작업 진행 정도를 표시하는 용도로 사용됨
        imgView.setImageBitmap(bitMap)

        //textView.setText(values[0].toString())
        super.onProgressUpdate(*values)
    }

    override fun onPostExecute(result: Boolean?) {  //사용자 UI에 ProgressBar를 표시하여 본격적인 작업 스레드에 들아기기 전에 작업 진행줄을 표시하는 구현이 들어감
        super.onPostExecute(result)
    }

    override fun onCancelled(result: Boolean?) {    //doInBackground() 실행 도중 중단되는 경우에 실행됨.
        super.onCancelled(result)
    }
}