package org.ktoggl.internal.retrofit

import org.ktoggl.internal.retrofit.dto.*
import retrofit2.Call
import retrofit2.http.*

internal interface TogglApi {

    @GET("me")
    fun getMe(): Call<UserResponse>

    @PUT("me")
    fun updateMe(): Call<UserResponse>

    @GET("workspaces")
    fun workspaces(): Call<List<Workspace>>

    @GET("workspaces/{workspaceId}/projects")
    fun workspaceProjects(@Path("workspaceId") workspaceId: Long): Call<List<Project>>

    @POST("time_entries")
    fun createTimeEntry(@Body timeEntryRequest: TimeEntryRequest): Call<TimeEntryResponse>
}