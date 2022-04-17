package com.natashaval.numbertrivia.database

import androidx.room.*
import com.natashaval.numbertrivia.model.NumberData
import kotlinx.coroutines.flow.Flow

@Dao
interface NumberDao {
    @Query("SELECT * FROM numbers WHERE is_favorite = 1 ORDER BY number ASC")
    fun getAllNumbers(): Flow<List<NumberData>>

    @Query("SELECT * FROM numbers WHERE number=:number")
    fun getNumber(number: Long): Flow<NumberData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNumber(numberData: NumberData)

    @Update
    suspend fun updateNumber(numberData: NumberData)

    @Delete
    suspend fun deleteNumber(numberData: NumberData)

    @Query("SELECT * FROM numbers WHERE number=:number AND description=:description")
    suspend fun getNumberDataFromTrivia(number: Long, description: String): NumberData?
}