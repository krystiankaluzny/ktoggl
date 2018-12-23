package org.ktoggl.internal

import org.ktoggl.TimeUtilProvider
import org.ktoggl.TogglTimeEntryClient
import org.ktoggl.entity.CreateTimeEntryData
import org.ktoggl.entity.TimeEntry
import org.ktoggl.internal.retrofit.TogglApi
import org.ktoggl.internal.retrofit.dto.TimeEntryRequest

internal class TogglTimeEntryClientImpl(private val p: TimeUtilProvider, private val togglApi: TogglApi) : TogglTimeEntryClient {

    companion object {
        private const val TAG = "TogglTimeEntityClient"
    }

    override fun createTimeEntry(timeEntryData: CreateTimeEntryData): TimeEntry {

        val internalTimeEntry = timeEntryData.toInternal(p, TAG)
        val timeEntryRequest = TimeEntryRequest(internalTimeEntry)
        val timeEntryResponse = togglApi.createTimeEntry(timeEntryRequest).execute().body()!!

        return timeEntryResponse.timeEntry.toExternal(p)
    }

    override fun getTimeEntry(timeEntryId: Long): TimeEntry {
        return TimeEntry(startTimestamp = 12L)
    }

    override fun getRunningTimeEntry(): TimeEntry? {
        return null
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

    override fun updateTimeEntriesTags(timeEntryIds: List<Long>, tags: List<String>, updateTagsAction: org.ktoggl.TogglTimeEntryClient.UpdateTagsAction) {
    }
}