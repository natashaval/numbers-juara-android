package com.natashaval.numbertrivia.repository

import com.natashaval.numbertrivia.network.NumberService
import javax.inject.Inject

class NumberRepository @Inject constructor(private val service: NumberService) {
  suspend fun getNumber(number: String?, type: String?) = service.getNumber(number, type)
}