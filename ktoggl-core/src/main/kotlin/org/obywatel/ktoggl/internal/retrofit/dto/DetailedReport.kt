package org.obywatel.ktoggl.internal.retrofit.dto

internal data class DetailedReportResponse(
    val total_count: Int,
    val per_page: Int,
    val total_grand: Long?,
    val total_billable: Long?,
    val total_currencies: List<CurrencyAmount>,
    val data: List<DetailedTimeEntry>
)

internal data class DetailedTimeEntry(
    val id: Long,
    val pid: Long?,
    val project: String?,
    val client: String?,
    val tid: Long?,
    val task: String?,
    val uid: Long?,
    val user: String?,
    val description: String?,
    val start: String,
    val end: String?,
    val dur: Long?,
    val updated: String,
    val use_stop: Boolean,
    val is_billable: Boolean,
    val billable: Double?,
    val cur: String?,
    val tags: List<String>
)