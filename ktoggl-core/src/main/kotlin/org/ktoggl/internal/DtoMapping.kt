package org.ktoggl.internal

import org.ktoggl.TimeUtilProvider
import org.ktoggl.entity.*
import org.ktoggl.fromHexColorToInt
import org.ktoggl.internal.retrofit.dto.DetailedReportResponse


internal fun org.ktoggl.internal.retrofit.dto.User.toExternal(p: TimeUtilProvider) =
    User(
        id = id,
        apiToken = api_token,
        defaultWorkspaceId = default_wid,
        fullName = fullname,
        email = email,
        beginningOfWeek = Day.fromValue(beginning_of_week),
        language = language,
        timezone = timezone,
        imageUrl = image_url ?: "",
        creationTimestamp = p.toEpochSecond(created_at),
        lastUpdateTimestamp = p.toEpochSecond(at)
    )

internal fun UserData.toInternal() =
    org.ktoggl.internal.retrofit.dto.UserData(
        email = email,
        fullname = fullName,
        timeofday_format = null,
        date_format = null,
        store_start_and_stop_time = null,
        beginning_of_week = beginningOfWeek?.value,
        send_product_emails = null,
        send_weekly_report = null,
        send_timer_notifications = null,
        timezone = timezone
    )

internal fun UserPassword.toInternal() =
    org.ktoggl.internal.retrofit.dto.UserPassword(
        current_password = currentPassword,
        password = newPassword
    )

internal fun org.ktoggl.internal.retrofit.dto.Workspace.toExternal(p: TimeUtilProvider) =
    Workspace(
        id = id,
        name = name,
        admin = admin,
        defaultCurrency = default_currency,
        logoUrl = logo_url ?: "",
        premium = premium,
        rounding = RoundingType.fromValue(rounding),
        roundingMinutes = rounding_minutes,
        onlyAdminsMayCreateProjects = only_admins_may_create_projects,
        onlyAdminsSeeBillableRates = only_admins_see_billable_rates,
        defaultHourlyRate = default_hourly_rate ?: 0.0,
        lastUpdateTimestamp = p.toEpochSecond(at)
    )
internal fun WorkspaceData.toInternal() =
    org.ktoggl.internal.retrofit.dto.WorkspaceData(
        name = name,
        default_currency = defaultCurrency,
        default_hourly_rate = defaultHourlyRate,
        only_admins_may_create_projects = onlyAdminsMayCreateProjects,
        only_admins_see_billable_rates = onlyAdminsSeeBillableRates,
        rounding = rounding?.value,
        rounding_minutes = roundingMinutes
    )

internal fun org.ktoggl.internal.retrofit.dto.Project.toExternal() =
    Project(
        id = id,
        name = name,
        workspaceId = wid,
        clientId = cid,
        active = active,
        private = is_private,
        colorId = color,
        color = hex_color.fromHexColorToInt()
    )


internal fun org.ktoggl.internal.retrofit.dto.CurrencyAmount.toExternal() =
    CurrencyAmount(
        currency = currency,
        amount = amount
    )

internal fun org.ktoggl.internal.retrofit.dto.DetailedTimeEntry.toExternal(p: TimeUtilProvider) =
    DetailedTimeEntry(
        id = id,
        client = client,
        project = pid?.let { DetailedTimeEntry.Info(it, project!!) },
        task = tid?.let { DetailedTimeEntry.Info(it, task!!) },
        user = uid?.let { DetailedTimeEntry.Info(it, user!!) },
        description = description,
        startTimestamp = p.toEpochSecond(start),
        endTimestamp = p.toEpochSecond(end),
        durationMillis = dur,
        lastUpdateTimestamp = p.toEpochSecond(updated),
        useStop = use_stop,
        billable = is_billable,
        payment = billable,
        currency = cur,
        tags = tags
    )

internal fun DetailedReportResponse.toExternal(p: TimeUtilProvider) =
    DetailedReport(
        totalCount = total_count,
        perPage = per_page,
        totalGrand = total_grand,
        totalPayment = total_billable,
        totalCurrencies = total_currencies.map { it.toExternal() },
        detailedTimeEntries = data.map { it.toExternal(p) }
    )

internal fun org.ktoggl.internal.retrofit.dto.TimeEntry.toExternal(p: TimeUtilProvider) =
    TimeEntry(
        id = id!!,
        description = description,
        workspaceId = wid,
        projectId = pid,
        taskId = tid,
        billable = billable,
        startTimestamp = p.toEpochSecond(start)!!,
        endTimestamp = p.toEpochSecond(stop),
        durationSeconds = duration!!,
        tags = tags ?: emptyList(),
        lastUpdateTimestamp = p.toEpochSecond(at)!!
    )

internal fun TimeEntry.toInternal(p: TimeUtilProvider) =
    org.ktoggl.internal.retrofit.dto.TimeEntry(
        id = id,
        description = description,
        wid = workspaceId,
        pid = projectId,
        tid = taskId,
        billable = billable,
        start = p.secondsToOffsetDateTimeStr(startTimestamp),
        stop = p.secondsToOffsetDateTimeStr(endTimestamp),
        duration = durationSeconds,
        created_with = "",
        tags = tags,
        at = null
    )

internal fun CreateTimeEntryData.toInternal(p: TimeUtilProvider, createdWith: String) =
    org.ktoggl.internal.retrofit.dto.TimeEntry(
        id = null,
        description = description,
        wid = if (parent is WorkspaceParent) parent.id else null,
        pid = if (parent is ProjectParent) parent.id else null,
        tid = if (parent is TaskParent) parent.id else null,
        billable = billable,
        start = p.secondsToOffsetDateTimeStr(startTimestamp),
        stop = p.secondsToOffsetDateTimeStr(endTimestamp),
        duration = (endTimestamp ?: 0) - startTimestamp,
        created_with = createdWith,
        tags = tags,
        at = null
    )

internal fun StartTimeEntryData.toInternal(createdWith: String) =
    org.ktoggl.internal.retrofit.dto.TimeEntry(
        id = null,
        description = description,
        wid = if (parent is WorkspaceParent) parent.id else null,
        pid = if (parent is ProjectParent) parent.id else null,
        tid = if (parent is TaskParent) parent.id else null,
        billable = billable,
        start = null,
        stop = null,
        duration = null,
        created_with = createdWith,
        tags = tags,
        at = null
    )

internal fun UpdateTimeEntryData.toInternal(p: TimeUtilProvider) =
    org.ktoggl.internal.retrofit.dto.TimeEntry(
        id = null,
        description = description,
        wid = if (parent is WorkspaceParent) parent.id else null,
        pid = if (parent is ProjectParent) parent.id else null,
        tid = if (parent is TaskParent) parent.id else null,
        billable = billable,
        start = p.secondsToOffsetDateTimeStr(startTimestamp),
        stop = p.secondsToOffsetDateTimeStr(endTimestamp),
        duration = if (endTimestamp != null && startTimestamp != null) endTimestamp - startTimestamp else null,
        created_with = null,
        tags = tags,
        at = null
    )

internal fun org.ktoggl.internal.retrofit.dto.Tag.toExternal() =
    Tag(
        id = id!!,
        workspaceId = wid!!,
        name = name
    )