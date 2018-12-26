package org.ktoggl.internal

import org.ktoggl.TimeUtilProvider
import org.ktoggl.TogglWorkspaceClient
import org.ktoggl.entity.*
import org.ktoggl.internal.retrofit.TogglApi
import org.ktoggl.internal.retrofit.dto.WorkspaceResponse
import org.ktoggl.internal.retrofit.dto.WorkspaceUpdateRequest

internal class TogglWorkspaceClientImpl(private val p: TimeUtilProvider, private val togglApi: TogglApi) : TogglWorkspaceClient {

    override fun getWorkspaces(): List<Workspace> {

        val workspaces = togglApi.getWorkspaces().execute().body() ?: emptyList()

        return workspaces.map { it.toExternal(p) }
    }

    override fun getWorkspace(workspaceId: Long): Workspace? {
        return togglApi.getWorkspace(workspaceId).execute().body()?.data?.toExternal(p)
    }

    override fun updateWorkspace(workspaceId: Long, workspaceData: WorkspaceData): Workspace {

        val workspaceUpdateRequest = WorkspaceUpdateRequest(workspaceData.toInternal())
        val body: WorkspaceResponse = togglApi.updateWorkspace(workspaceId, workspaceUpdateRequest).execute().body()!!

        return body.data.toExternal(p)
    }


    override fun getWorkspaceProjects(workspaceId: Long, projectStateFilter: ProjectStateFilter): List<Project> {

        val active = when (projectStateFilter) {
            ProjectStateFilter.ACTIVE -> "true"
            ProjectStateFilter.INACTIVE -> "false"
            else -> "both"
        }

        val workspaceProjects = togglApi.getWorkspaceProjects(workspaceId, active, "false", "false").execute().body() ?: emptyList()

        return workspaceProjects.map { it.toExternal() }
    }

    override fun getWorkspaceTags(workspaceId: Long): List<Tag> {

        val tags = togglApi.getWorkspaceTags(workspaceId).execute().body() ?: emptyList()

        return tags.map { it.toExternal() }
    }
}