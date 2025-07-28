package com.example.kingburguer.di

import android.content.Context
import com.example.kingburguer.api.KingBurguerService
import com.example.kingburguer.api.KingBurguerService.Companion.BASE_URL
import com.example.kingburguer.data.KingBurguerLocalStorage
import com.example.kingburguer.data.KingBurguerRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providesLocalStorage(@ApplicationContext context: Context): KingBurguerLocalStorage {
        return KingBurguerLocalStorage(context)
    }

    @Provides
    @Singleton
    fun providesService(): KingBurguerService {

        val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val clientOk = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(clientOk)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(KingBurguerService::class.java)

    }

    @Provides
    @Singleton
    fun providesRepository(
        api: KingBurguerService,
        localStorage: KingBurguerLocalStorage
    ): KingBurguerRepository {

        return KingBurguerRepository(api, localStorage)
    }
}