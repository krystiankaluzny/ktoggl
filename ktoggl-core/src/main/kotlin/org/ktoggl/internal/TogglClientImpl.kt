package org.ktoggl.internal

import org.ktoggl.TimeUtilProvider
import org.ktoggl.TogglClient
import org.ktoggl.entity.*
import org.ktoggl.internal.retrofit.TogglApi
import org.ktoggl.internal.retrofit.TogglReportApi
import org.ktoggl.request.DetailedReportParameters
import org.ktoggl.request.SummaryReportParameters
import org.ktoggl.request.WeeklyReportParameters

internal class TogglClientImpl(p: TimeUtilProvider, togglApi: TogglApi, togglReportApi: TogglReportApi) : TogglClient {

    private val togglUserClient = TogglUserClientImpl(p, togglApi)
    private val togglWorkspaceClient = TogglWorkspaceClientImpl(p, togglApi)
    private val togglTimeEntityClient = TogglTimeEntryClientImpl(p, togglApi)
    private val togglTagClient = TogglTagClientImpl(togglApi)
    private val togglReportClient = TogglReportClientImpl(p, togglReportApi)

    override fun getCurrentUser() = togglUserClient.getCurrentUser()
    override fun updateCurrentUser(userData: UserData) = togglUserClient.updateCurrentUser(userData)
    override fun updateCurrentUserPassword(userPassword: UserPassword): User = togglUserClient.updateCurrentUserPassword(userPassword)

    override fun getWorkspaces() = togglWorkspaceClient.getWorkspaces()
    override fun getWorkspace(workspaceId: Long) = togglWorkspaceClient.getWorkspace(workspaceId)
    override fun updateWorkspace(workspaceId: Long, workspaceData: WorkspaceData)= togglWorkspaceClient.updateWorkspace(workspaceId, workspaceData)
    override fun getWorkspaceProjects(workspaceId: Long, projectStateFilter: ProjectStateFilter) = togglWorkspaceClient.getWorkspaceProjects(workspaceId, projectStateFilter)
    override fun getWorkspaceTags(workspaceId: Long) = togglWorkspaceClient.getWorkspaceTags(workspaceId)

    override fun createTimeEntry(timeEntryData: CreateTimeEntryData) = togglTimeEntityClient.createTimeEntry(timeEntryData)
    override fun startTimeEntry(timeEntryData: StartTimeEntryData) = togglTimeEntityClient.startTimeEntry(timeEntryData)
    override fun stopTimeEntry(timeEntry: TimeEntry) = togglTimeEntityClient.stopTimeEntry(timeEntry)
    override fun getTimeEntry(timeEntryId: Long) = togglTimeEntityClient.getTimeEntry(timeEntryId)
    override fun getRunningTimeEntry() = togglTimeEntityClient.getRunningTimeEntry()
    override fun updateTimeEntry(timeEntry: TimeEntry) = togglTimeEntityClient.updateTimeEntry(timeEntry)
    override fun deleteTimeEntry(timeEntryId: Long) = togglTimeEntityClient.deleteTimeEntry(timeEntryId)
    override fun updateTimeEntriesTags(timeEntryIds: List<Long>, tags: List<String>, updateTagsAction: org.ktoggl.TogglTimeEntryClient.UpdateTagsAction) = togglTimeEntityClient.updateTimeEntriesTags(timeEntryIds, tags, updateTagsAction)

    override fun createTag(workspaceId: Long, name: String) = togglTagClient.createTag(workspaceId, name)
    override fun updateTag(tagId: Long, newName: String) = togglTagClient.updateTag(tagId, newName)
    override fun deleteTag(tagId: Long) = togglTagClient.deleteTag(tagId)

    override fun getWeeklyReport(workspaceId: Long, weeklyReportParameters: WeeklyReportParameters) = togglReportClient.getWeeklyReport(workspaceId, weeklyReportParameters)
    override fun getDetailedReport(workspaceId: Long, detailedReportParameters: DetailedReportParameters) = togglReportClient.getDetailedReport(workspaceId, detailedReportParameters)
    override fun getSummaryReport(workspaceId: Long, summaryReportParameters: SummaryReportParameters) = togglReportClient.getSummaryReport(workspaceId, summaryReportParameters)
}
