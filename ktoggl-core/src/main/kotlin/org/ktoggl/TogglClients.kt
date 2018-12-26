package org.ktoggl

import org.ktoggl.entity.*
import org.ktoggl.request.DetailedReportParameters
import org.ktoggl.request.SummaryReportParameters
import org.ktoggl.request.WeeklyReportParameters


interface TogglClient :
    TogglUserClient,
    TogglWorkspaceClient,
    TogglTimeEntryClient,
    TogglTagClient,
    TogglReportClient

interface TogglUserClient {

    fun getCurrentUser(): User
    fun updateCurrentUser(userData: UserData): User
    fun updateCurrentUserPassword(userPassword: UserPassword): User
}

interface TogglWorkspaceClient {

    fun getWorkspaces(): List<Workspace>
    fun getWorkspace(workspaceId: Long) : Workspace?
    fun updateWorkspace(workspaceId: Long, workspaceData: WorkspaceData) : Workspace
    fun getWorkspaceProjects(workspaceId: Long, projectStateFilter: ProjectStateFilter = ProjectStateFilter.ACTIVE): List<Project>
    fun getWorkspaceTags(workspaceId: Long): List<Tag>
}

interface TogglTimeEntryClient {

    fun createTimeEntry(timeEntryData: CreateTimeEntryData): TimeEntry
    fun startTimeEntry(timeEntryData: StartTimeEntryData): TimeEntry
    fun stopTimeEntry(timeEntryId: Long): TimeEntry
    fun getTimeEntry(timeEntryId: Long): TimeEntry?
    fun getCurrentTimeEntry(): TimeEntry?
    fun updateTimeEntry(timeEntry: TimeEntry): TimeEntry
    fun deleteTimeEntry(timeEntryId: Long): Boolean
    fun updateTimeEntriesTags(timeEntryIds: List<Long>, tags: List<String>, updateTagsAction: org.ktoggl.TogglTimeEntryClient.UpdateTagsAction)

    enum class UpdateTagsAction {
        OVERRIDE, ADD, REMOVE
    }
}

interface TogglTagClient {

    fun createTag(workspaceId: Long, name: String): Tag
    fun updateTag(tagId: Long, newName: String): Tag
    fun deleteTag(tagId: Long): Boolean
}

interface TogglReportClient {

    fun getWeeklyReport(workspaceId: Long, weeklyReportParameters: WeeklyReportParameters = WeeklyReportParameters())
    fun getDetailedReport(workspaceId: Long, detailedReportParameters: DetailedReportParameters = DetailedReportParameters()): DetailedReport
    fun getSummaryReport(workspaceId: Long, summaryReportParameters: SummaryReportParameters = SummaryReportParameters())
}