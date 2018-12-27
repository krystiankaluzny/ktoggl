package org.ktoggl.internal

import org.ktoggl.TimeUtilProvider
import org.ktoggl.TogglTimeEntryClient
import org.ktoggl.TogglTimeEntryClient.UpdateTagsAction.*
import org.ktoggl.entity.CreateTimeEntryData
import org.ktoggl.entity.StartTimeEntryData
import org.ktoggl.entity.TimeEntry
import org.ktoggl.entity.UpdateTimeEntryData
import org.ktoggl.internal.retrofit.TogglApi
import org.ktoggl.internal.retrofit.dto.TagsTimeEntry
import org.ktoggl.internal.retrofit.dto.TagsTimeEntryRequest
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

    override fun updateTimeEntriesTags(timeEntryIds: List<Long>, tags: List<String>, updateTagsAction: org.ktoggl.TogglTimeEntryClient.UpdateTagsAction): List<TimeEntry> {

        if (timeEntryIds.isEmpty()) return emptyList()

        var timeEntries = updateTimeEntriesTagsCall(timeEntryIds, tags, updateTagsAction)

        if (updateTagsAction == REMOVE) {

            timeEntries = clearAllTagsIfAnyNotRemoved(timeEntries, tags)
        }

        return timeEntries.map { it.toExternal(p) }
    }

    private fun updateTimeEntriesTagsCall(timeEntryIds: List<Long>, tags: List<String>, updateTagsAction: TogglTimeEntryClient.UpdateTagsAction): List<org.ktoggl.internal.retrofit.dto.TimeEntry> {

        if (timeEntryIds.isEmpty()) return emptyList()

        val timeEntriesIdsString = timeEntryIds.joinToString(separator = ",")
        val tagAction = when (updateTagsAction) {
            ADD -> "add"
            REMOVE -> "remove"
            OVERRIDE -> null
        }

        val tagsTimeEntryRequest = TagsTimeEntryRequest(TagsTimeEntry(tags, tagAction))
        return togglApi.updateTimeEntriesTags(timeEntriesIdsString, tagsTimeEntryRequest).execute().body()!!.timeEntries
    }

    // Fix https://github.com/toggl/toggl_api_docs/issues/200
    private fun clearAllTagsIfAnyNotRemoved(timeEntries: List<org.ktoggl.internal.retrofit.dto.TimeEntry>, tags: List<String>): List<org.ktoggl.internal.retrofit.dto.TimeEntry> {

        val timeEntriesIdsToRemoveAllTags = mutableListOf<Long>()
        val timeEntriesResponse = ArrayList<org.ktoggl.internal.retrofit.dto.TimeEntry>(timeEntries.size)

        timeEntries.forEach {
            if (it.tags?.intersect(tags)?.isNotEmpty() == true) {
                timeEntriesIdsToRemoveAllTags.add(it.id!!)
                timeEntriesResponse.add(it.copy(tags = emptyList()))
            } else {
                timeEntriesResponse.add(it)
            }
        }

        updateTimeEntriesTagsCall(timeEntriesIdsToRemoveAllTags, emptyList(), OVERRIDE)

        return timeEntriesResponse
    }


}