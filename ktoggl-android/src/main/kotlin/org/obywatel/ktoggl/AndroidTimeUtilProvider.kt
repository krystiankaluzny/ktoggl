package org.obywatel.ktoggl

import org.threeten.bp.Instant
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

internal class AndroidTimeUtilProvider : TimeUtilProvider {

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