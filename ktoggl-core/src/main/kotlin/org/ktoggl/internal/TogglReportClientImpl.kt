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
import kotlin.math.min

internal class TogglReportClientImpl(private val p: TimeUtilProvider, private val togglReportApi: TogglReportApi) : TogglReportClient {

    private companion object {
        const val USER_AGENT = "TogglClient"
        const val SEC_IN_YEAR = 365 * 24 * 60 * 60
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

        val detailedResponsePerPages = mutableListOf<DetailedReportResponse>()

        val fromTimestamp = detailedReportParameters.baseReportParameters.fromTimestamp
        val toTimestamp = detailedReportParameters.baseReportParameters.toTimestamp

        if (fromTimestamp != null && toTimestamp != null) {
            var from = fromTimestamp
            var to = min(from + SEC_IN_YEAR, toTimestamp)

            do {
                params["since"] = p.secondsToLocalDateStr(from)
                params["until"] = p.secondsToLocalDateStr(to)

                requestAllPagesOfDetailedReport(params, detailedResponsePerPages)

                from += SEC_IN_YEAR
                to = min(from + SEC_IN_YEAR, toTimestamp)
            } while (from < to)

        } else {
            requestAllPagesOfDetailedReport(params, detailedResponsePerPages)
        }

        return detailedResponsePerPages.toExternal(p)

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

        baseReportParameters.userAgent.let { params["user_agent"] = it ?: USER_AGENT }
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

    private fun requestAllPagesOfDetailedReport(params: MutableMap<String, String>, container: MutableList<DetailedReportResponse>) {

        var detailedReportResponse: DetailedReportResponse
        var page = 0

        do {
            detailedReportResponse = requestDetailedReportPage(params, ++page)
            container.add(detailedReportResponse)
        } while (detailedReportResponse.per_page * page < detailedReportResponse.total_count)
    }

    private fun requestDetailedReportPage(params: MutableMap<String, String>, page: Int): DetailedReportResponse {

        params["page"] = page.toString()
        return togglReportApi.detailed(params).execute().body() ?: DetailedReportResponse.EMPTY
    }
}