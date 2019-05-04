package org.ktoggl.entity

data class TimeEntry(
    val id: Long,
    val workspaceId: Long? = null,
    val projectId: Long? = null,
    val taskId: Long? = null,
    val description: String? = null,
    val billable: Boolean? = null,
    val startTimestamp: Long,
    val endTimestamp: Long? = null,
    val durationSeconds: Long,
    val tags: List<String> = emptyList(),
    val lastUpdateTimestamp: Long = 0
)

data class CreateTimeEntryData(
    val parent: TimeEntryParent,
    val description: String? = null,
    val billable: Boolean? = null,
    val startTimestamp: Long,
    val endTimestamp: Long,
    val tags: List<String> = emptyList()
)

data class StartTimeEntryData(
    val parent: TimeEntryParent,
    val description: String? = null,
    val billable: Boolean? = null,
    val tags: List<String> = emptyList()
)

/**
 * If startTimestamp and endTimestamp are defined then durationSeconds is ignored
 */
data class UpdateTimeEntryData(
    val parent: TimeEntryParent? = null,
    val description: String? = null,
    val billable: Boolean? = null,
    val startTimestamp: Long? = null,
    val endTimestamp: Long? = null,
    val durationSeconds: Long? = null,
    val tags: List<String>? = null
)

open class TimeEntryParent(var id: Long)
class TaskParent(id: Long) : TimeEntryParent(id)
class ProjectParent(id: Long) : TimeEntryParent(id)
class WorkspaceParent(id: Long) : TimeEntryParent(id)