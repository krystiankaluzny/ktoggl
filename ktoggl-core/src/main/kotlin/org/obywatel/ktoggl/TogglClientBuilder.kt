package org.obywatel.ktoggl

import org.obywatel.ktoggl.internal.TogglClientImpl
import org.obywatel.ktoggl.internal.retrofit.RetrofitFactory
import org.obywatel.ktoggl.internal.retrofit.TogglApi
import org.obywatel.ktoggl.internal.retrofit.TogglReportApi

open class TogglClientBuilder(private var p: TimeUtilProvider) {

    private fun togglApi(apiToken: String) = RetrofitFactory.create<TogglApi>(apiToken, "https://www.toggl.com/api/v8/")
    private fun togglReportApi(apiToken: String) = RetrofitFactory.create<TogglReportApi>(apiToken, "https://toggl.com/reports/api/v2/")

    fun build(apiToken: String): org.obywatel.ktoggl.TogglClient = TogglClientImpl(p, togglApi(apiToken), togglReportApi(apiToken))
}
