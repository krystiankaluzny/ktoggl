package org.ktoggl.entity

data class Project(
    val id: Long,
    val name: String,
    val workspaceId: Long,
    val clientId: Long?,
    val active: Boolean,
    val private: Boolean,
    val colorId: Int,
    val color: Int
)