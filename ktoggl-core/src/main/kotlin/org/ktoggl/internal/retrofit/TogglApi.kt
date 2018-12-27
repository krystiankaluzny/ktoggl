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
    fun getWorkspaceProjects(@Path("workspaceId") workspaceId: Long,
                             @Query("active") active: String,
                             @Query("actual_hours") activeHours: String,
                             @Query("only_templates") onlyTemplates: String): Call<List<Project>>

    @GET("workspaces/{workspaceId}/tags")
    fun getWorkspaceTags(@Path("workspaceId") workspaceId: Long): Call<List<Tag>>

    @POST("time_entries")
    fun createTimeEntry(@Body timeEntryRequest: TimeEntryRequest): Call<TimeEntryResponse>

    @POST("time_entries/start")
    fun startTimeEntry(@Body timeEntryRequest: TimeEntryRequest): Call<TimeEntryResponse>

    @PUT("time_entries/{timeEntryId}/stop")
    fun stopTimeEntry(@Path("timeEntryId") timeEntryId: Long): Call<TimeEntryResponse>

    @GET("time_entries/{timeEntryId}")
    fun getTimeEntry(@Path("timeEntryId") timeEntryId: Long): Call<TimeEntryResponse>

    @GET("time_entries/current")
    fun getCurrentTimeEntry(): Call<TimeEntryResponse>

    @PUT("time_entries/{timeEntryId}")
    fun updateTimeEntry(@Path("timeEntryId") timeEntryId: Long, @Body timeEntryRequest: TimeEntryRequest): Call<TimeEntryResponse>

    @DELETE("time_entries/{timeEntryId}")
    fun deleteTimeEntry(@Path("timeEntryId") timeEntryId: Long): Call<Void>

    @GET("time_entries")
    fun getTimeEntriesStartedInRange(@Query("start_date") startDate: String, @Query("end_date") endDate: String): Call<List<TimeEntry>>

    @POST("tags")
    fun createTag(@Body tagRequest: TagRequest): Call<TagResponse>

    @PUT("tags/{tagId}")
    fun updateTag(@Path("tagId") tagId: Long, @Body tagRequest: TagRequest): Call<TagResponse>

    @DELETE("tags/{tagId}")
    fun deleteTag(@Path("tagId") tagId: Long): Call<Void>
}