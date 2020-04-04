package com.example.jogtrackerapp.netwok.models.api

import com.google.gson.annotations.SerializedName


data class Response(

	@field:SerializedName("jogs")
	val jogs: List<JogsItem?>? = null
)