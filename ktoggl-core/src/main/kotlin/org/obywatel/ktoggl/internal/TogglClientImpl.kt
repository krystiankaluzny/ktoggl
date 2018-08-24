package org.obywatel.ktoggl.internal

import org.obywatel.ktoggl.TimeUtilProvider
import org.obywatel.ktoggl.TogglClient
import org.obywatel.ktoggl.entity.TimeEntry
import org.obywatel.ktoggl.internal.retrofit.TogglApi
import org.obywatel.ktoggl.internal.retrofit.TogglReportApi
import org.obywatel.ktoggl.request.DetailedReportParameters
import org.obywatel.ktoggl.request.SummaryReportParameters
import org.obywatel.ktoggl.request.WeeklyReportParameters

internal class TogglClientImpl(p: TimeUtilProvider, togglApi: TogglApi, togglReportApi: TogglReportApi) : TogglClient {

    private val togglUserClient = TogglUserClientImpl(p, togglApi)
    private val togglWorkspaceClient = TogglWorkspaceClientImpl(p, togglApi)
    private val togglTimeEntityClient = TogglTimeEntityClientImpl(p, togglApi)
    private val togglReportClient = TogglReportClientImpl(p, togglReportApi)

    override fun getCurrentUser() = togglUserClient.getCurrentUser()

    override fun getWorkspaces() = togglWorkspaceClient.getWorkspaces()
    override fun getWorkspaceProjects(id: Long) = togglWorkspaceClient.getWorkspaceProjects(id)

    override fun getTimeEntry(timeEntryId: Long) = togglTimeEntityClient.getTimeEntry(timeEntryId)
    override fun getRunningTimeEntry() = togglTimeEntityClient.getRunningTimeEntry()
    override fun createTimeEntry(timeEntry: TimeEntry) = togglTimeEntityClient.createTimeEntry(timeEntry)
    override fun startTimeEntry(timeEntry: TimeEntry) = togglTimeEntityClient.startTimeEntry(timeEntry)
    override fun stopTimeEntry(timeEntry: TimeEntry) = togglTimeEntityClient.stopTimeEntry(timeEntry)
    override fun updateTimeEntry(timeEntry: TimeEntry) = togglTimeEntityClient.updateTimeEntry(timeEntry)
    override fun deleteTimeEntry(timeEntryId: Long) = togglTimeEntityClient.deleteTimeEntry(timeEntryId)
    override fun updateTimeEntriesTags(timeEntryIds: List<Long>, tags: List<String>, updateTagsAction: org.obywatel.ktoggl.TogglTimeEntryClient.UpdateTagsAction) = togglTimeEntityClient.updateTimeEntriesTags(timeEntryIds, tags, updateTagsAction)

    override fun getWeeklyReport(workspaceId: Long, weeklyReportParameters: WeeklyReportParameters) = togglReportClient.getWeeklyReport(workspaceId, weeklyReportParameters)
    override fun getDetailedReport(workspaceId: Long, detailedReportParameters: DetailedReportParameters) = togglReportClient.getDetailedReport(workspaceId, detailedReportParameters)
    override fun getSummaryReport(workspaceId: Long, summaryReportParameters: SummaryReportParameters) = togglReportClient.getSummaryReport(workspaceId, summaryReportParameters)
}
