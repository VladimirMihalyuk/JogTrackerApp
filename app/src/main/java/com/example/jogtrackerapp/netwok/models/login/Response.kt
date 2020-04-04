package com.example.jogtrackerapp.netwok.models.login

import com.google.gson.annotations.SerializedName


data class Response(

	@field:SerializedName("access_token")
	val accessToken: String? = null,

	@field:SerializedName("scope")
	val scope: String? = null,

	@field:SerializedName("created_at")
	val createdAt: Int? = null,

	@field:SerializedName("token_type")
	val tokenType: String? = null,

	@field:SerializedName("expires_in")
	val expiresIn: Int? = null
)