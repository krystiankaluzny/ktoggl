package org.ktoggl.jvm.entity

import org.ktoggl.entity.User
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset

val User.lastUpdateTime: OffsetDateTime
    get() = OffsetDateTime.ofInstant(Instant.ofEpochSecond(lastUpdateTimestamp), ZoneOffset.UTC)

val User.creationTime: OffsetDateTime
    get() = OffsetDateTime.ofInstant(Instant.ofEpochSecond(creationTimestamp), ZoneOffset.UTC)
