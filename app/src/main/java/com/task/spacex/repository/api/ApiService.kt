package com.task.spacex.repository.api

import com.task.spacex.repository.LaunchRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/v4/launches/query")
    suspend fun getLaunches(@Body request: LaunchRequest): LaunchResponse

}
