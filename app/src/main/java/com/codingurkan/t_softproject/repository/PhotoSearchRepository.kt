package com.codingurkan.t_softproject.repository

import com.codingurkan.t_softproject.service.ApiService
import com.codingurkan.t_softproject.util.API_KEY
import javax.inject.Inject

class PhotoSearchRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun photoListWithSearch(q: String?, page: Int, perPage: Int)
            = apiService.photoRequest(q, API_KEY, page, perPage)
}