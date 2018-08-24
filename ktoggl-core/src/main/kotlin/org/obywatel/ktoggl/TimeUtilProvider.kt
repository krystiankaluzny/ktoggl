package org.obywatel.ktoggl

import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

interface TimeUtilProvider {
    fun toEpochSecond(str: String): Long
    fun toEpochSecond(str: String?): Long?
    fun secondsToOffsetDateTimeStr(seconds: Long): String
    fun secondsToOffsetDateTimeStr(seconds: Long?): String?
    fun secondsToLocalDateStr(seconds: Long): String
    fun secondsToLocalDateStr(seconds: Long?): String?
    fun currentEpochSecond(): Long
}

class TimeConverterImpl : TimeUtilProvider {
    override fun toEpochSecond(str: String): Long = OffsetDateTime.parse(str, DateTimeFormatter.ISO_OFFSET_DATE_TIME).toEpochSecond()
    override fun toEpochSecond(str: String?): Long? = str?.let { toEpochSecond(it) }

    override fun secondsToOffsetDateTimeStr(seconds: Long): String {
        val offsetDateTime = OffsetDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneId.systemDefault())
        return DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(offsetDateTime)
    }

    override fun secondsToOffsetDateTimeStr(seconds: Long?): String? = seconds?.let { secondsToOffsetDateTimeStr(it) }

    override fun secondsToLocalDateStr(seconds: Long): String {
        val offsetDateTime = OffsetDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneId.systemDefault())
        return DateTimeFormatter.ISO_LOCAL_DATE.format(offsetDateTime.toLocalDate())
    }

    override fun secondsToLocalDateStr(seconds: Long?): String? = seconds?.let { secondsToOffsetDateTimeStr(it) }

    override fun currentEpochSecond(): Long = OffsetDateTime.now().toEpochSecond()
}