package com.natashaval.numbertrivia.injection

import android.content.Context
import androidx.room.Room
import com.natashaval.numbertrivia.database.NumberDao
import com.natashaval.numbertrivia.database.NumberDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
  @Singleton
  @Provides fun provideNumberDatabase(
    @ApplicationContext appContext: Context
  ): NumberDatabase {
    return Room.databaseBuilder(
      appContext, NumberDatabase::class.java, "trivia_db"
    ).fallbackToDestructiveMigration().build()
  }

  @Singleton
  @Provides
  fun provideNumberDao(db: NumberDatabase): NumberDao {
    return db.getNumberDao()
  }
}