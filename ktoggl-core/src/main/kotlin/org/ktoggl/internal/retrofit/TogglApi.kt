package org.ktoggl.internal.retrofit

import org.ktoggl.internal.retrofit.dto.*
import retrofit2.Call
import retrofit2.http.*

internal interface TogglApi {

    @GET("me")
    fun getMe(): Call<UserResponse>

    @PUT("me")
    fun updateMe(@Body user: UserUpdateRequest): Call<UserResponse>

    @GET("workspaces")
    fun getWorkspaces(): Call<List<Workspace>>

    @GET("workspaces/{workspaceId}")
    fun getWorkspace(@Path("workspaceId") workspaceId: Long): Call<WorkspaceResponse>

    @PUT("workspaces/{workspaceId}")
    fun updateWorkspace(@Path("workspaceId") workspaceId: Long, @Body workspace: WorkspaceUpdateRequest): Call<WorkspaceResponse>

    @GET("workspaces/{workspaceId}/projects")
    fun workspaceProjects(@Path("workspaceId") workspaceId: Long,
                          @Query("active") active: String,
                          @Query("actual_hours") activeHours: String,
                          @Query("only_templates") onlyTemplates: String): Call<List<Project>>

    @POST("time_entries")
    fun createTimeEntry(@Body timeEntryRequest: TimeEntryRequest): Call<TimeEntryResponse>
}