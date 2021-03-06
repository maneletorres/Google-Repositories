package com.example.googlerepositories.di

import android.app.Application
import androidx.room.Room
import androidx.viewbinding.BuildConfig
import com.example.googlerepositories.api.webservice.ApiRest
import com.example.googlerepositories.data.RepositoryDatabase
import com.example.googlerepositories.util.BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        // .cache()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) BODY else NONE
        }).build()

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi, client: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .client(client)
        .build()

    @Provides
    @Singleton
    fun provideApiRest(retrofit: Retrofit): ApiRest = retrofit.create(ApiRest::class.java)

    @Provides
    @Singleton
    fun provideDatabase(app: Application): RepositoryDatabase =
        Room.databaseBuilder(app, RepositoryDatabase::class.java, "repository_database")
            //.addMigrations(migration)
            .build()

    @Provides
    @Singleton
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO
}