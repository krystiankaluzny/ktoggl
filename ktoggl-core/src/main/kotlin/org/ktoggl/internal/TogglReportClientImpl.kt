package org.ktoggl.internal

import org.ktoggl.TimeUtilProvider
import org.ktoggl.TogglReportClient
import org.ktoggl.entity.DetailedReport
import org.ktoggl.internal.retrofit.TogglReportApi
import org.ktoggl.internal.retrofit.dto.DetailedReportResponse
import org.ktoggl.request.BaseReportParameters
import org.ktoggl.request.DetailedReportParameters
import org.ktoggl.request.SummaryReportParameters
import org.ktoggl.request.WeeklyReportParameters

internal class TogglReportClientImpl(private val p: TimeUtilProvider, private val togglReportApi: TogglReportApi) : TogglReportClient {

    private companion object {
        const val userAgent = "TogglClient"
        val emptyDetailedReport = DetailedReport(0, 0, 0, 0, emptyList(), emptyList())
    }

    override fun getWeeklyReport(workspaceId: Long, weeklyReportParameters: WeeklyReportParameters) {
        val params = createBaseRequestParams(workspaceId, weeklyReportParameters.baseReportParameters)

        weeklyReportParameters.weeklyOrder?.let {
            params["order_field"] = it.field
            params["order_desc"] = if (it.ascending) "off" else "on"
        }
    }

    override fun getDetailedReport(workspaceId: Long, detailedReportParameters: DetailedReportParameters): DetailedReport {
        val params = createBaseRequestParams(workspaceId, detailedReportParameters.baseReportParameters)

        detailedReportParameters.detailedOrder?.let {
            params["order_field"] = it.field
            params["order_desc"] = if (it.ascending) "off" else "on"
        }

        if (detailedReportParameters.page > 0) {
            params["page"] = detailedReportParameters.page.toString()

            val detailedReport = togglReportApi.detailed(params).execute().body() ?: return emptyDetailedReport

            return detailedReport.toExternal(p)

        } else {
            params["page"] = "1"

            var detailedReportResponse = togglReportApi.detailed(params).execute().body() ?: return emptyDetailedReport

            if (detailedReportResponse.total_count > detailedReportResponse.per_page) {

                val pageCount = detailedReportResponse.total_count / detailedReportResponse.per_page + 1

                val detailedResponsePerPages = ArrayList<DetailedReportResponse>(pageCount)
                detailedResponsePerPages.add(detailedReportResponse)

                for (page in 2..pageCount) {
                    params["page"] = page.toString()
                    detailedReportResponse = togglReportApi.detailed(params).execute().body() ?: return emptyDetailedReport
                    detailedResponsePerPages.add(detailedReportResponse)
                }

                return detailedResponsePerPages.toExternal(p)

            } else {
                return detailedReportResponse.toExternal(p)
            }
        }
    }

    override fun getSummaryReport(workspaceId: Long, summaryReportParameters: SummaryReportParameters) {
        val params = createBaseRequestParams(workspaceId, summaryReportParameters.baseReportParameters)

        summaryReportParameters.summaryOrder?.let {
            params["order_field"] = it.field
            params["order_desc"] = if (it.ascending) "off" else "on"
        }
    }

    private fun createBaseRequestParams(workspaceId: Long, baseReportParameters: BaseReportParameters): MutableMap<String, String> {

        val separator = ","
        val params = mutableMapOf<String, String>()

        params["workspace_id"] = workspaceId.toString()

        baseReportParameters.userAgent.let { params["user_agent"] = it ?: userAgent }
        baseReportParameters.fromTimestamp?.let { params["since"] = p.secondsToLocalDateStr(it) }
        baseReportParameters.toTimestamp?.let { params["until"] = p.secondsToLocalDateStr(it) }
        baseReportParameters.billable?.let { params["billable"] = it.value }
        baseReportParameters.clientIds.let { params["client_ids"] = it.joinToString(separator = separator) }
        baseReportParameters.membersOfGroupIds.let { params["members_of_group_ids"] = it.joinToString(separator = separator) }
        baseReportParameters.orMembersOfGroupIds.let { params["or_members_of_group_ids"] = it.joinToString(separator = separator) }
        baseReportParameters.tagIds.let { params["tag_ids"] = it.joinToString(separator = separator) }
        baseReportParameters.taskIds.let { params["task_ids"] = it.joinToString(separator = separator) }
        baseReportParameters.timeEntryIds.let { params["time_entry_ids"] = it.joinToString(separator = separator) }
        baseReportParameters.description?.let { params["description"] = it }
        baseReportParameters.withoutDescription?.let { params["without_description"] = if (it) "true" else "false" }
        baseReportParameters.distinctRates?.let { params["distinct_rates"] = if (it) "on" else "off" }
        baseReportParameters.rounding?.let { params["rounding"] = if (it) "on" else "off" }

        return params
    }
}