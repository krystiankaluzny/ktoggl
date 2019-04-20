package org.ktoggl.jvm.request

import org.ktoggl.request.BaseReportParameters
import org.ktoggl.request.Billable
import java.time.LocalDate

class JvmBaseReportParameters(
    userAgent: String? = null,
    fromDate: LocalDate? = null,
    toDate: LocalDate? = null,
    billable: Billable? = null,
    clientIds: List<Long> = emptyList(),
    projectIds: List<Long> = emptyList(),
    userIds: List<Long> = emptyList(),
    membersOfGroupIds: List<Long> = emptyList(),
    orMembersOfGroupIds: List<Long> = emptyList(),
    tagIds: List<Long> = emptyList(),
    taskIds: List<Long> = emptyList(),
    timeEntryIds: List<Long> = emptyList(),
    description: String? = null,
    withoutDescription: Boolean? = null,
    distinctRates: Boolean? = null,
    rounding: Boolean? = null
) : BaseReportParameters(
    userAgent,
    fromDate?.toEpochDay()?.times(24 * 60 * 60),
    toDate?.toEpochDay()?.times(24 * 60 * 60),
    billable,
    clientIds,
    projectIds,
    userIds,
    membersOfGroupIds,
    orMembersOfGroupIds,
    tagIds,
    taskIds,
    timeEntryIds,
    description,
    withoutDescription,
    distinctRates,
    rounding
)
