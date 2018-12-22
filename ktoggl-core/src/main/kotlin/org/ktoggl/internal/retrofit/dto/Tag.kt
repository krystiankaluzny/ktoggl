package org.ktoggl.internal.retrofit.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

internal data class TagResponse(
    @JsonProperty("data") val tag: Tag
)

internal data class TagRequest(
    @JsonProperty("tag") val tag: Tag
)

@JsonInclude(JsonInclude.Include.NON_NULL)
internal data class Tag(
    val id: Long?,
    val wid: Long?,
    val name: String,
    val at: String? = null
)