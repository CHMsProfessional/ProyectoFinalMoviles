package com.example.practicapersonasapi.models

import com.google.gson.annotations.SerializedName

data class QRCodeReserva(
    @SerializedName("code")
    val code: String?
) {

}