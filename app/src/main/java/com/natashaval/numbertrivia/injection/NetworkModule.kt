package com.natashaval.numbertrivia.injection

import com.natashaval.numbertrivia.network.NumberService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
  private const val BASE_URL = "http://numbersapi.com/"

  @Singleton
  @Provides
  fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
      .addConverterFactory(ScalarsConverterFactory.create())
      .baseUrl(BASE_URL)
      .build()
  }

  @Singleton
  @Provides
  fun provideNumberService(retrofit: Retrofit): NumberService {
    return retrofit.create(NumberService::class.java)
  }
}