package org.ktoggl

import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.Test
import org.ktoggl.android.JvmTogglClientBuilder

class TogglWorkspaceClientIT {

    private val apiToken = "0723f331421f3860603b497ba01f790d"
    private val togglWorkspaceClient: TogglWorkspaceClient = JvmTogglClientBuilder().build(apiToken)

    @Test
    fun `getWorkspaces should return one test user's workspace`() {

        val workspaces = togglWorkspaceClient.getWorkspaces()

        workspaces.size shouldEqualTo 1
        workspaces.first()
            .apply {
                id shouldEqualTo 2963000
//                name shouldBeEqualTo "h"
            }
    }
}