package com.example.practicapersonasapi.models

import com.google.gson.annotations.SerializedName

data class ScheduleIDRequest(
    @SerializedName("schedule_id")
    val scheduleId: Int
)