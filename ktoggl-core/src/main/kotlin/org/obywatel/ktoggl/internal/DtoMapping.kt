package org.obywatel.ktoggl.internal

import org.obywatel.ktoggl.entity.*
import org.obywatel.ktoggl.fromHexColorToInt
import org.obywatel.ktoggl.internal.retrofit.dto.DetailedReportResponse
import org.obywatel.ktoggl.secondsToOffsetDateTimeStr
import org.obywatel.ktoggl.toEpochSecond


internal fun org.obywatel.ktoggl.internal.retrofit.dto.User.toExternal() =
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
        creationTimestamp = created_at.toEpochSecond(),
        lastUpdateTimestamp = at.toEpochSecond()
    )

internal fun org.obywatel.ktoggl.internal.retrofit.dto.Workspace.toExternal() =
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
        lastUpdateTimestamp = at.toEpochSecond()
    )

internal fun org.obywatel.ktoggl.internal.retrofit.dto.Project.toExternal() =
    Project(
        id = id,
        name = name,
        workspaceId = wid,
        clientId = cid,
        active = active,
        private = is_private,
        creationTimestamp = at.toEpochSecond(),
        colorId = color,
        color = hex_color.fromHexColorToInt()
    )


internal fun org.obywatel.ktoggl.internal.retrofit.dto.CurrencyAmount.toExternal() =
    CurrencyAmount(
        currency = currency,
        amount = amount
    )

internal fun org.obywatel.ktoggl.internal.retrofit.dto.DetailedTimeEntry.toExternal() =
    DetailedTimeEntry(
        id = id,
        client = client,
        project = pid?.let { DetailedTimeEntry.Info(it, project!!) },
        task = tid?.let { DetailedTimeEntry.Info(it, task!!) },
        user = uid?.let { DetailedTimeEntry.Info(it, user!!) },
        description = description,
        startTimestamp = start.toEpochSecond(),
        endTimestamp = end?.toEpochSecond(),
        durationMillis = dur,
        lastUpdateTimestamp = updated.toEpochSecond(),
        useStop = use_stop,
        billable = is_billable,
        payment = billable,
        currency = cur,
        tags = tags
    )

internal fun DetailedReportResponse.toExternal() =
    DetailedReport(
        totalCount = total_count,
        perPage = per_page,
        totalGrand = total_grand,
        totalPayment = total_billable,
        totalCurrencies = total_currencies.map { it.toExternal() },
        detailedTimeEntries = data.map { it.toExternal() }
    )

internal fun org.obywatel.ktoggl.internal.retrofit.dto.TimeEntry.toExternal() =
    TimeEntry(
        id = id,
        description = description,
        workspaceId = wid,
        projectId = pid,
        taskId = tid,
        billable = billable,
        startTimestamp = start.toEpochSecond(),
        endTimestamp = stop?.toEpochSecond(),
        durationSeconds = duration,
        tags = tags,
        lastUpdateTimestamp = at?.toEpochSecond()
    )

internal fun TimeEntry.toInternal() =
    org.obywatel.ktoggl.internal.retrofit.dto.TimeEntry(
        id = id,
        description = description,
        wid = workspaceId,
        pid = projectId,
        tid = taskId,
        billable = billable,
        start = startTimestamp?.secondsToOffsetDateTimeStr(),
        stop = endTimestamp?.secondsToOffsetDateTimeStr(),
        duration = durationSeconds,
        created_with = "",
        tags = tags,
        at = lastUpdateTimestamp?.secondsToOffsetDateTimeStr()
    )