package org.ktoggl.entity

import org.ktoggl.EnumCompanion

data class Workspace(
    val id: Long,
    val name: String,
    val premium: Boolean,
    val admin: Boolean,
    val defaultHourlyRate: Double,
    val defaultCurrency: String,
    val onlyAdminsMayCreateProjects: Boolean,
    val onlyAdminsSeeBillableRates: Boolean,
    val rounding: RoundingType,
    val roundingMinutes: Int,
    val lastUpdateTimestamp: Long,
    val logoUrl: String
)

enum class RoundingType(val value: Byte) {
    ROUND_DOWN(-1),
    NEAREST(0),
    ROUND_UP(1);

    companion object : EnumCompanion<Byte, RoundingType>(RoundingType.values().associateBy(RoundingType::value))
}

data class WorkspaceData(
    val name: String? = null,
    val defaultHourlyRate: Double? = null,
    val defaultCurrency: String? = null,
    val onlyAdminsMayCreateProjects: Boolean? = null,
    val onlyAdminsSeeBillableRates: Boolean? = null,
    val rounding: RoundingType? = null,
    val roundingMinutes: Int? = null
)