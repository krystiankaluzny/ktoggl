package org.ktoggl.internal.retrofit

import org.ktoggl.internal.retrofit.dto.DetailedReportResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

internal interface TogglReportApi {

    @GET("weekly")
    fun weekly(@QueryMap queryParams: Map<String, String>) : Call<DetailedReportResponse>

    @GET("details")
    fun detailed(@QueryMap queryParams: Map<String, String>) : Call<DetailedReportResponse>

    @GET("summary")
    fun summary(@QueryMap queryParams: Map<String, String>) : Call<DetailedReportResponse>
}