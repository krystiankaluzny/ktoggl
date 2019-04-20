package org.ktoggl.jvm.entity

import org.ktoggl.entity.DetailedTimeEntry
import java.time.Duration
import java.time.OffsetDateTime

val DetailedTimeEntry.startTime: OffsetDateTime
    get() = java.time.OffsetDateTime.ofInstant(java.time.Instant.ofEpochSecond(startTimestamp), java.time.ZoneOffset.UTC)

val DetailedTimeEntry.endTime: OffsetDateTime?
    get() = if (endTimestamp != null) java.time.OffsetDateTime.ofInstant(java.time.Instant.ofEpochSecond(endTimestamp!!), java.time.ZoneOffset.UTC) else null

val DetailedTimeEntry.lastUpdateTime: OffsetDateTime
    get() = java.time.OffsetDateTime.ofInstant(java.time.Instant.ofEpochSecond(lastUpdateTimestamp), java.time.ZoneOffset.UTC)

val DetailedTimeEntry.duration: Duration?
    get() = if (endTimestamp != null) Duration.between(startTime, endTime) else null