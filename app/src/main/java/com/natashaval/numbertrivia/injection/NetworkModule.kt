package com.natashaval.numbertrivia.injection

import com.natashaval.numbertrivia.network.NumberService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
  private const val BASE_URL = "http://numbersapi.com/"

  @Singleton
  @Provides
  fun provideOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor { message ->
      Timber.tag("OkHttp").d(message)
    }.apply {
      level = HttpLoggingInterceptor.Level.BODY
    }
    return OkHttpClient.Builder()
      .addInterceptor(httpLoggingInterceptor)
      .build()
  }

  @Singleton
  @Provides
  fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .addConverterFactory(ScalarsConverterFactory.create())
      .client(okHttpClient)
      .baseUrl(BASE_URL)
      .build()
  }

  @Singleton
  @Provides
  fun provideNumberService(retrofit: Retrofit): NumberService {
    return retrofit.create(NumberService::class.java)
  }
}