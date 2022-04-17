package com.natashaval.numbertrivia.database

import androidx.room.*
import com.natashaval.numbertrivia.model.NumberData
import kotlinx.coroutines.flow.Flow

@Dao
interface NumberDao {
    @Query("SELECT * FROM numbers ORDER BY number ASC")
    fun getAllNumbers(): Flow<List<NumberData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNumber(numberData: NumberData)

    @Delete
    suspend fun deleteNumber(numberData: NumberData)

    @Query("SELECT * FROM numbers WHERE number=:number AND description=:description")
    suspend fun getNumberDataFromTrivia(number: String, description: String): NumberData?
}