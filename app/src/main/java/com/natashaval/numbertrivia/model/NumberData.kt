package com.natashaval.numbertrivia.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "numbers")
data class NumberData(
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "id")
  val id: Int = 0,
  @ColumnInfo(name = "number")
  val number: String,
  @ColumnInfo(name = "is_favorite")
  val isFavorite: Boolean = false
)
