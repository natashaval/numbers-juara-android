package com.natashaval.numbertrivia.repository

import com.natashaval.numbertrivia.database.NumberDao
import com.natashaval.numbertrivia.model.NumberData
import com.natashaval.numbertrivia.network.NumberService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NumberRepository @Inject constructor(
  private val service: NumberService,
  private val dao: NumberDao
) {
  fun getAllNumbers() = dao.getAllNumbers()

  suspend fun getNumberApi(number: String?, type: String?) = service.getNumber(number, type)

  suspend fun insertNumberData(numberData: NumberData) = dao.insertNumber(numberData)

  suspend fun getNumberDataFromTrivia(number: String, description: String) = dao.getNumberDataFromTrivia(number, description)
}