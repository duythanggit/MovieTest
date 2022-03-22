package com.duythangjr.test.network

import com.duythangjr.test.model.PopularResponse
import retrofit2.Response
import retrofit2.http.GET

interface API {
    @GET("movie?api_key=26763d7bf2e94098192e629eb975dab0&page=1")
    suspend fun getPopular(): Response<PopularResponse>
}