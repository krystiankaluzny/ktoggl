package org.ktoggl

interface TimeUtilProvider {
    fun toEpochSecond(str: String): Long
    fun toEpochSecond(str: String?): Long?
    fun secondsToOffsetDateTimeStr(seconds: Long): String
    fun secondsToOffsetDateTimeStr(seconds: Long?): String?
    fun secondsToLocalDateStr(seconds: Long): String
    fun secondsToLocalDateStr(seconds: Long?): String?
    fun currentEpochSecond(): Long
}