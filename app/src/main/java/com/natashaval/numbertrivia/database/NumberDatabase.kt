package com.natashaval.numbertrivia.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.natashaval.numbertrivia.model.NumberData

@Database(entities = [NumberData::class], version = 3, exportSchema = false)
abstract class NumberDatabase : RoomDatabase() {
  abstract fun getNumberDao() : NumberDao
}