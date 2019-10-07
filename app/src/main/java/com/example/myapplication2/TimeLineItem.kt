package com.example.myapplication2

data class TimeLineItem(

    var timelineExpenditure: Boolean,   //지출인지 수입인지 (true 지출, false 수입)
    var timelineImgSource: Int,
    var timeLinePlusMinus : String,
    var timeLineMoney: String,
    var timeLineContents: String,
    var timeLineHours: String,
    var timeLineMinutes: String
)