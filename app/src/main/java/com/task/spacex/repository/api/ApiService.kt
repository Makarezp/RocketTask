package com.task.spacex.repository.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("/v4/launches/query")
    suspend fun getLaunches(@Body request: LaunchRequest): LaunchResponse

    @GET("v4/company")
    suspend fun getCompanyInfo(): CompanyInfoResponse

}
