package org.ktoggl.jvm.entity

import org.ktoggl.TogglTimeEntryClient
import org.ktoggl.entity.TimeEntry
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset

val TimeEntry.lastUpdateTime: OffsetDateTime
    get() = OffsetDateTime.ofInstant(Instant.ofEpochSecond(lastUpdateTimestamp), ZoneOffset.UTC)

val TimeEntry.startTime: OffsetDateTime
    get() = OffsetDateTime.ofInstant(Instant.ofEpochSecond(startTimestamp), ZoneOffset.UTC)

val TimeEntry.endTime: OffsetDateTime?
    get() = if (endTimestamp != null) OffsetDateTime.ofInstant(Instant.ofEpochSecond(endTimestamp!!), ZoneOffset.UTC) else null

fun TogglTimeEntryClient.getTimeEntriesStartedInRange(fromDate: OffsetDateTime, toDate: OffsetDateTime): List<TimeEntry> {
    return getTimeEntriesStartedInRange(fromDate.toEpochSecond(), toDate.toEpochSecond())
}