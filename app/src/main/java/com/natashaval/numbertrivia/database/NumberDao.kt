package com.natashaval.numbertrivia.database

import androidx.room.*
import com.natashaval.numbertrivia.model.NumberData
import kotlinx.coroutines.flow.Flow

@Dao
interface NumberDao {
    @Query("SELECT * FROM numbers ORDER BY number ASC")
    fun getAllNumbers(): Flow<List<NumberData>>

    @Query("SELECT * FROM numbers WHERE number=:number")
    fun getNumber(number: String): Flow<NumberData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNumber(numberData: NumberData)

    @Delete
    suspend fun deleteNumber(numberData: NumberData)
}