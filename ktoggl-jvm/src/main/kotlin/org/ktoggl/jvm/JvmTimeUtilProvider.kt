package org.ktoggl.jvm

import org.ktoggl.TimeUtilProvider
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

internal class JvmTimeUtilProvider : TimeUtilProvider {

    override fun toEpochSecond(str: String): Long = OffsetDateTime.parse(str, DateTimeFormatter.ISO_OFFSET_DATE_TIME).toEpochSecond()
    override fun toEpochSecond(str: String?): Long? = str?.let { toEpochSecond(it) }

    override fun secondsToOffsetDateTimeStr(seconds: Long): String {
        val offsetDateTime = OffsetDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneOffset.UTC)
        return DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(offsetDateTime)
    }

    override fun secondsToOffsetDateTimeStr(seconds: Long?): String? = seconds?.let { secondsToOffsetDateTimeStr(it) }

    override fun secondsToLocalDateStr(seconds: Long): String {
        val offsetDateTime = OffsetDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneOffset.UTC)
        return DateTimeFormatter.ISO_LOCAL_DATE.format(offsetDateTime.toLocalDate())
    }

    override fun secondsToLocalDateStr(seconds: Long?): String? = seconds?.let { secondsToOffsetDateTimeStr(it) }

    override fun currentEpochSecond(): Long = OffsetDateTime.now().toEpochSecond()
}