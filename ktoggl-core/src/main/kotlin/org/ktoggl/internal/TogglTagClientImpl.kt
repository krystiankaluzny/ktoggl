package org.ktoggl.internal

import org.ktoggl.TogglTagClient
import org.ktoggl.entity.Tag
import org.ktoggl.internal.retrofit.TogglApi
import org.ktoggl.internal.retrofit.dto.TagRequest

internal class TogglTagClientImpl(private val togglApi: TogglApi) : TogglTagClient {

    override fun createTag(workspaceId: Long, name: String): Tag {
        val tagDto = org.ktoggl.internal.retrofit.dto.Tag(null, workspaceId, name)
        return togglApi.createTag(TagRequest(tagDto)).execute().body()!!.tag.toExternal()
    }

    override fun updateTag(tagId: Long, newName: String): Tag {
        val tagDto = org.ktoggl.internal.retrofit.dto.Tag(null, null, newName)
        return togglApi.updateTag(tagId, TagRequest(tagDto)).execute().body()!!.tag.toExternal()
    }

    override fun deleteTag(tagId: Long): Boolean = togglApi.deleteTag(tagId).execute().isSuccessful
}