package com.example.jogtrackerapp.netwok.models.login


import com.google.gson.annotations.SerializedName


data class ResponseToken(

	@field:SerializedName("response")
	val response: Response? = null,

	@field:SerializedName("timestamp")
	val timestamp: Int? = null
)