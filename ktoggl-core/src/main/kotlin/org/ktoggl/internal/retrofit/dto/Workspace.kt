package org.ktoggl.internal.retrofit.dto

import com.fasterxml.jackson.annotation.JsonInclude

internal data class WorkspaceResponse(
    val data: Workspace
)

internal data class Workspace(
    val id: Long,
    val name: String,
    val premium: Boolean,
    val admin: Boolean,
    val default_hourly_rate: Double?,
    val default_currency: String,
    val only_admins_may_create_projects: Boolean,
    val only_admins_see_billable_rates: Boolean,
    val rounding: Byte,
    val rounding_minutes: Int,
    val at: String,
    val logo_url: String?
)

internal data class WorkspaceUpdateRequest(
    val workspace: WorkspaceData
)

@JsonInclude(JsonInclude.Include.NON_NULL)
internal data class WorkspaceData(
    val name: String?,
    val default_hourly_rate: Double?,
    val default_currency: String?,
    val only_admins_may_create_projects: Boolean?,
    val only_admins_see_billable_rates: Boolean?,
    val rounding: Byte?,
    val rounding_minutes: Int?
)