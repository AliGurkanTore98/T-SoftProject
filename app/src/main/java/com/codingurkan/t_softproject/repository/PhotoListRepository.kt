package com.codingurkan.t_softproject.repository

import com.codingurkan.t_softproject.service.ApiService
import com.codingurkan.t_softproject.util.API_KEY
import javax.inject.Inject

class PhotoListRepository @Inject constructor(private val apiService : ApiService) {

    suspend fun photoListRequest(pages : Int, perPage: Int) = apiService.photoRequest(null,API_KEY,pages,perPage)

}