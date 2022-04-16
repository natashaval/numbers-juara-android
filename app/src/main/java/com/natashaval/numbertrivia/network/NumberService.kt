package com.natashaval.numbertrivia.network

import retrofit2.http.GET
import retrofit2.http.Path

interface NumberService {
  @GET("/{number}/{type}")
  suspend fun getNumber(
    @Path("number") number: String? = "random",
    @Path("type") type: String? = null
  ): String
}