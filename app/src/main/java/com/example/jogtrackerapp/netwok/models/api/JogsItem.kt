package com.example.jogtrackerapp.netwok.models.api


import com.google.gson.annotations.SerializedName


data class JogsItem(

	@field:SerializedName("date")
	val date: Long? = null,

	@field:SerializedName("distance")
	val distance: Double? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("time")
	val time: Int? = null
)