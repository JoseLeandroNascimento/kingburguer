package com.example.kingburguer.auth.di

import com.example.kingburguer.api.KingBurguerService
import com.example.kingburguer.auth.data.AuthRepositoryImpl
import com.example.kingburguer.auth.domain.AuthRepository
import com.example.kingburguer.data.KingBurguerLocalStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun providesRepository(
        service: KingBurguerService,
        localStorage: KingBurguerLocalStorage
    ): AuthRepository {
        return AuthRepositoryImpl(
            service,
            localStorage
        )
    }

}