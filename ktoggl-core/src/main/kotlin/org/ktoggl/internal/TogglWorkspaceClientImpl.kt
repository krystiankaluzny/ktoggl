package org.ktoggl.internal

import org.ktoggl.TimeUtilProvider
import org.ktoggl.TogglWorkspaceClient
import org.ktoggl.entity.Project
import org.ktoggl.entity.Workspace
import org.ktoggl.entity.WorkspaceData
import org.ktoggl.internal.retrofit.TogglApi
import org.ktoggl.internal.retrofit.dto.WorkspaceResponse
import org.ktoggl.internal.retrofit.dto.WorkspaceUpdateRequest

internal class TogglWorkspaceClientImpl(private val p: TimeUtilProvider, private val togglApi: TogglApi) : TogglWorkspaceClient {

    override fun getWorkspaces(): List<Workspace> {

        val workspaces = togglApi.getWorkspaces().execute().body() ?: return emptyList()

        return workspaces.map { it.toExternal(p) }
    }

    override fun getWorkspace(workspaceId: Long): Workspace? = togglApi.getWorkspace(workspaceId).execute().body()?.data?.toExternal(p)

    override fun updateWorkspace(workspaceId: Long, workspaceData: WorkspaceData): Workspace {

        val workspaceUpdateRequest = WorkspaceUpdateRequest(workspaceData.toInternal())
        val body: WorkspaceResponse = togglApi.updateWorkspace(workspaceId, workspaceUpdateRequest).execute().body()!!

        return body.data.toExternal(p)
    }


    override fun getWorkspaceProjects(id: Long): List<Project> {

        val workspaceProjects = togglApi.workspaceProjects(id).execute().body() ?: return emptyList()

        return workspaceProjects.map { it.toExternal(p) }
    }
}