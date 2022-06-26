package com.codingurkan.t_softproject.service

import com.codingurkan.t_softproject.model.PhotoResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
interface ApiService {



    @GET("/api/")
    suspend fun photoRequest(
        @Query("q") query : String?,
        @Query("key") apiKey: String,
        @Query("page") page: Int,
        @Query("per_page") per_page : Int
    ) : Response<PhotoResponseModel>

}