package org.obywatel.ktoggl.internal

import org.obywatel.ktoggl.TogglWorkspaceClient
import org.obywatel.ktoggl.entity.Project
import org.obywatel.ktoggl.entity.Workspace
import org.obywatel.ktoggl.internal.retrofit.TogglApi

internal class TogglWorkspaceClientImpl(private val togglApi: TogglApi) : org.obywatel.ktoggl.TogglWorkspaceClient {

    override fun getWorkspaces(): List<Workspace> {

        val workspaces = togglApi.workspaces().execute().body() ?: return emptyList()

        return workspaces.map { it.toExternal() }
    }

    override fun getWorkspaceProjects(id: Long): List<Project> {

        val workspaceProjects = togglApi.workspaceProjects(id).execute().body() ?: return emptyList()

        return workspaceProjects.map { it.toExternal() }
    }
}