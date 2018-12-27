package org.ktoggl.internal

import org.ktoggl.TimeUtilProvider
import org.ktoggl.TogglTimeEntryClient
import org.ktoggl.entity.CreateTimeEntryData
import org.ktoggl.entity.StartTimeEntryData
import org.ktoggl.entity.TimeEntry
import org.ktoggl.entity.UpdateTimeEntryData
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

        return timeEntryResponse.timeEntry!!.toExternal(p)
    }

    override fun startTimeEntry(timeEntryData: StartTimeEntryData): TimeEntry {

        val internalTimeEntry = timeEntryData.toInternal(TAG)
        val timeEntryRequest = TimeEntryRequest(internalTimeEntry)
        val timeEntryResponse = togglApi.startTimeEntry(timeEntryRequest).execute().body()!!

        return timeEntryResponse.timeEntry!!.toExternal(p)
    }

    override fun stopTimeEntry(timeEntryId: Long): TimeEntry {
        return togglApi.stopTimeEntry(timeEntryId).execute().body()!!.timeEntry!!.toExternal(p)
    }

    override fun getTimeEntry(timeEntryId: Long): TimeEntry? {
        return togglApi.getTimeEntry(timeEntryId).execute().body()?.timeEntry?.toExternal(p)
    }

    override fun getCurrentTimeEntry(): TimeEntry? {
        return togglApi.getCurrentTimeEntry().execute().body()?.timeEntry?.toExternal(p)
    }

    override fun updateTimeEntry(timeEntryId: Long, timeEntryData: UpdateTimeEntryData): TimeEntry {

        val internalTimeEntry = timeEntryData.toInternal(p)
        val timeEntryRequest = TimeEntryRequest(internalTimeEntry)
        val timeEntryResponse = togglApi.updateTimeEntry(timeEntryId, timeEntryRequest).execute().body()!!

        return timeEntryResponse.timeEntry!!.toExternal(p)
    }

    override fun deleteTimeEntry(timeEntryId: Long): Boolean = togglApi.deleteTimeEntry(timeEntryId).execute().isSuccessful

    override fun getTimeEntriesStartedInRange(fromTimestamp: Long, toTimestamp: Long): List<TimeEntry> {

        val startDate = p.secondsToOffsetDateTimeStr(fromTimestamp)
        val endDate = p.secondsToOffsetDateTimeStr(toTimestamp)

        val timeEntries = togglApi.getTimeEntriesStartedInRange(startDate, endDate).execute().body() ?: emptyList()
        return timeEntries.map { it.toExternal(p) }
    }

    override fun updateTimeEntriesTags(timeEntryIds: List<Long>, tags: List<String>, updateTagsAction: org.ktoggl.TogglTimeEntryClient.UpdateTagsAction) {
    }
}