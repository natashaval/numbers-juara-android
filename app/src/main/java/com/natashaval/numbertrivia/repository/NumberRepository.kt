package com.natashaval.numbertrivia.repository

import com.natashaval.numbertrivia.database.NumberDao
import com.natashaval.numbertrivia.model.NumberData
import com.natashaval.numbertrivia.network.NumberService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class NumberRepository @Inject constructor(
  private val service: NumberService,
  private val dao: NumberDao
) {
  fun getAllNumbers() = dao.getAllNumbers()

  suspend fun getNumberApi(number: String?, type: String?) = service.getNumber(number, type)

  private suspend fun insertNumberData(numberData: NumberData) = dao.insertNumber(numberData)
  private suspend fun updateNumberData(numberData: NumberData) = dao.updateNumber(numberData)

  fun getNumberData(number: Long): Flow<NumberData> = dao.getNumber(number)

  suspend fun getNumberDataFromTrivia(number: Long, description: String) = dao.getNumberDataFromTrivia(number, description)

  suspend fun insertOrUpdate(numberData: NumberData) {
    val result = getNumberData(numberData.number).firstOrNull()
    if (null == result) {
      insertNumberData(numberData)
    } else {
      updateNumberData(numberData)
    }
  }
}