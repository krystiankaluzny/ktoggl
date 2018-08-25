package org.ktoggl.internal

import org.ktoggl.TimeUtilProvider
import org.ktoggl.TogglWorkspaceClient
import org.ktoggl.entity.Project
import org.ktoggl.entity.Workspace
import org.ktoggl.internal.retrofit.TogglApi

internal class TogglWorkspaceClientImpl(private val p: TimeUtilProvider, private val togglApi: TogglApi) : TogglWorkspaceClient {

    override fun getWorkspaces(): List<Workspace> {

        val workspaces = togglApi.workspaces().execute().body() ?: return emptyList()

        return workspaces.map { it.toExternal(p) }
    }

    override fun getWorkspaceProjects(id: Long): List<Project> {

        val workspaceProjects = togglApi.workspaceProjects(id).execute().body() ?: return emptyList()

        return workspaceProjects.map { it.toExternal(p) }
    }
}