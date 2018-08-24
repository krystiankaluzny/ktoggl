package org.obywatel.ktoggl.internal

import org.obywatel.ktoggl.TimeUtilProvider
import org.obywatel.ktoggl.TogglTimeEntryClient
import org.obywatel.ktoggl.entity.TimeEntry
import org.obywatel.ktoggl.internal.retrofit.TogglApi
import org.obywatel.ktoggl.internal.retrofit.dto.TimeEntryRequest

internal class TogglTimeEntityClientImpl(private val p: TimeUtilProvider, private val togglApi: TogglApi) : TogglTimeEntryClient {

    companion object {
        private const val TAG = "TogglTimeEntityClient"
    }

    override fun getTimeEntry(timeEntryId: Long): TimeEntry {
        return TimeEntry(startTimestamp = 12L)
    }

    override fun getRunningTimeEntry(): TimeEntry? {
        return null
    }

    override fun createTimeEntry(timeEntry: TimeEntry): TimeEntry? {

        if (timeEntry.startTimestamp == null)
            throw IllegalArgumentException("startTimestamp must be defined")

        if (timeEntry.workspaceId == null && timeEntry.projectId == null && timeEntry.taskId == null)
            throw IllegalArgumentException("At least one of the following must be defined: workspaceId, projectId, taskId")

        val duration = timeEntry.durationSeconds ?: ((timeEntry.endTimestamp ?: 0) - (timeEntry.startTimestamp ?: 0))

        val internalTimeEntry = timeEntry.toInternal(p).copy(id = null, created_with = TAG, stop = null, duration = duration)
        val timeEntryRequest = TimeEntryRequest(internalTimeEntry)
        val timeEntryResponse = togglApi.createTimeEntry(timeEntryRequest).execute().body()

        return timeEntryResponse?.timeEntry?.toExternal(p)
    }

    override fun startTimeEntry(timeEntry: TimeEntry): TimeEntry {
        return timeEntry
    }

    override fun stopTimeEntry(timeEntry: TimeEntry): TimeEntry {
        return timeEntry
    }

    override fun updateTimeEntry(timeEntry: TimeEntry): TimeEntry {
        return timeEntry
    }

    override fun deleteTimeEntry(timeEntryId: Long): Boolean {
        return false
    }

    override fun updateTimeEntriesTags(timeEntryIds: List<Long>, tags: List<String>, updateTagsAction: org.obywatel.ktoggl.TogglTimeEntryClient.UpdateTagsAction) {
    }
}