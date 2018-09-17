package org.ktoggl.android.request

import org.ktoggl.request.BaseReportParameters
import org.ktoggl.request.Billable

class AndroidBaseReportParameters(
    userAgent: String? = null,
    fromTimestamp: Long? = null,
    toTimestamp: Long? = null,
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
    fromTimestamp,
    toTimestamp,
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
    rounding)