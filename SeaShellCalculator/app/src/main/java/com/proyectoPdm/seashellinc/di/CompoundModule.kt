package com.proyectoPdm.seashellinc.di

import com.proyectoPdm.seashellinc.data.remote.CompoundApiService
import com.proyectoPdm.seashellinc.data.repository.CompoundRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CompoundModule {
    private const val BASE_URL = "" //TODO: Agregar la url de la api (investigar como aplicar variables de entorno)

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesCompoundApiService(retrofit: Retrofit) : CompoundApiService{
        return retrofit.create(CompoundApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesCompoundRepository(compoundApiService: CompoundApiService) : CompoundRepository {
        return CompoundRepository(compoundApiService)
    }
}
