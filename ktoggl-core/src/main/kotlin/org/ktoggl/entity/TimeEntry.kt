package org.ktoggl.entity

data class TimeEntry(
    var id: Long? = null,
    var workspaceId: Long? = null,
    var projectId: Long? = null,
    var taskId: Long? = null,
    var description: String? = null,
    var billable: Boolean? = null,
    var startTimestamp: Long? = null,
    var endTimestamp: Long? = null,
    var durationSeconds: Long? = null,
    var tags: List<String> = emptyList(),
    var lastUpdateTimestamp: Long? = null
)