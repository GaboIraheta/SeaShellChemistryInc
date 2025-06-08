package com.proyectoPdm.seashellinc.data.remote

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET

interface CompoundApiService {
    @GET(/*Api url*/)
    suspend fun getCompounds() : Response<CompoundApiResponse>  //TODO: Configurar que mande como argumentos el correo del usuario y el token de autenticación

    companion object {
        fun create(retrofit: Retrofit) : CompoundApiService {
            return retrofit.create(CompoundApiService::class.java)
        }
    }
}