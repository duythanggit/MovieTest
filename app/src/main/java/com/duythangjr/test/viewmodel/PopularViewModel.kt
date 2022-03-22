package com.duythangjr.test.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.duythangjr.test.model.PopularResponse
import com.duythangjr.test.util.NetworkUtils
import com.duythangjr.test.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class PopularViewModel(private val app: Application, private val repository: AppRepository) :
    AndroidViewModel(app) {

    var popularList: MutableLiveData<Resource<PopularResponse>> = MutableLiveData()

    init {
        getMovies()
    }

    private fun getMovies() {
        viewModelScope.launch {
            fetchMovies()
        }
    }

    private suspend fun fetchMovies() {
        popularList.postValue(Resource.Loading())
        try {
            if (NetworkUtils.hasInternetConnection(app)) {
                val response = repository.getPopular()
                popularList.postValue(handleResponse(response))
            } else {
                popularList.postValue(Resource.Error("No internet connection"))
            }
        } catch (e: Exception) {
            popularList.postValue(Resource.Error("Failed"))
        }

    }

    private fun handleResponse(response: Response<PopularResponse>): Resource<PopularResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

}