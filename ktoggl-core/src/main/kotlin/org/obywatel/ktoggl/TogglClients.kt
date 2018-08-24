package org.obywatel.ktoggl

import org.obywatel.ktoggl.entity.*
import org.obywatel.ktoggl.request.DetailedReportParameters
import org.obywatel.ktoggl.request.SummaryReportParameters
import org.obywatel.ktoggl.request.WeeklyReportParameters


interface TogglClient :
    TogglUserClient,
    TogglWorkspaceClient,
    TogglTimeEntryClient,
    TogglReportClient

interface TogglUserClient {

    fun getCurrentUser(): User?
}

interface TogglWorkspaceClient {

    fun getWorkspaces(): List<Workspace>
    fun getWorkspaceProjects(id: Long): List<Project>
}

interface TogglTimeEntryClient {

    fun getTimeEntry(timeEntryId: Long): TimeEntry
    fun getRunningTimeEntry(): TimeEntry?
    fun createTimeEntry(timeEntry: TimeEntry): TimeEntry?
    fun startTimeEntry(timeEntry: TimeEntry): TimeEntry
    fun stopTimeEntry(timeEntry: TimeEntry): TimeEntry
    fun updateTimeEntry(timeEntry: TimeEntry): TimeEntry
    fun deleteTimeEntry(timeEntryId: Long): Boolean
    fun updateTimeEntriesTags(timeEntryIds: List<Long>, tags: List<String>, updateTagsAction: org.obywatel.ktoggl.TogglTimeEntryClient.UpdateTagsAction)

    enum class UpdateTagsAction {
        OVERRIDE, ADD, REMOVE
    }
}

interface TogglReportClient {

    fun getWeeklyReport(workspaceId: Long, weeklyReportParameters: WeeklyReportParameters = WeeklyReportParameters())
    fun getDetailedReport(workspaceId: Long, detailedReportParameters: DetailedReportParameters = DetailedReportParameters()): DetailedReport
    fun getSummaryReport(workspaceId: Long, summaryReportParameters: SummaryReportParameters = SummaryReportParameters())
}