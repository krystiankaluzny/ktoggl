package org.ktoggl.entity

data class TimeEntry(
    var id: Long,
    var workspaceId: Long? = null,
    var projectId: Long? = null,
    var taskId: Long? = null,
    var description: String? = null,
    var billable: Boolean? = null,
    var startTimestamp: Long,
    var endTimestamp: Long? = null,
    var durationSeconds: Long,
    var tags: List<String> = emptyList(),
    var lastUpdateTimestamp: Long = 0
)

data class CreateTimeEntryData(
    var startTimestamp: Long,
    var endTimestamp: Long? = null,
    var parent: TimeEntryParent,
    var description: String? = null,
    var billable: Boolean? = null,
    var tags: List<String> = emptyList()
)

data class StartTimeEntryData(
    var parent: TimeEntryParent,
    var description: String? = null,
    var billable: Boolean? = null,
    var tags: List<String> = emptyList()
)

open class TimeEntryParent(var id: Long)
class TaskParent(id: Long) : TimeEntryParent(id)
class ProjectParent(id: Long) : TimeEntryParent(id)
class WorkspaceParent(id: Long) : TimeEntryParent(id)