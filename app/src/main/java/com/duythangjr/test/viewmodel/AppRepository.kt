package com.duythangjr.test.viewmodel

import com.duythangjr.test.network.RetrofitInstance

class AppRepository {

    suspend fun getPopular() = RetrofitInstance.popularApi.getPopular()

}