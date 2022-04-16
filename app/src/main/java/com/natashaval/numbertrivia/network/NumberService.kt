package com.natashaval.numbertrivia.network

import retrofit2.http.GET
import retrofit2.http.Path

interface NumberService {
  @GET suspend fun getNumber(
    @Path("number") number: Int,
    @Path("type") type: String? = null
  ): String
}