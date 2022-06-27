package com.melvin.ongandroid.data

import com.melvin.ongandroid.model.entities.AuthMethodsResponse
import com.melvin.ongandroid.model.entities.NewsResponse
import com.melvin.ongandroid.model.entities.UserRegistrationRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface OngApi {

    @POST("register")
    suspend fun postNewUser(
        @Body newUser: UserRegistrationRequest
    ) : AuthMethodsResponse

    @GET("news")
    suspend fun getNews() : NewsResponse
}