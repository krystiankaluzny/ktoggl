package org.ktoggl

import io.kotlintest.matchers.string.shouldBeEmpty
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec
import org.ktoggl.android.JvmTogglClientBuilder
import org.ktoggl.entity.RoundingType
import org.ktoggl.entity.WorkspaceData

class TogglWorkspaceClientSpec : StringSpec({

    val apiToken = "0723f331421f3860603b497ba01f790d"
    val togglWorkspaceClient: TogglWorkspaceClient = JvmTogglClientBuilder().build(apiToken)

    "getWorkspaces should return one test user's workspaces" {

        val workspaces = togglWorkspaceClient.getWorkspaces()

        workspaces.size shouldBe 1
        workspaces.first()
            .apply {
                id shouldBe 2963000
                name shouldBe "Enormous2calm4's workspace"
                premium shouldBe false
                admin shouldBe true
                defaultHourlyRate shouldBe 0.0
                defaultCurrency shouldBe "USD"
                onlyAdminsMayCreateProjects shouldBe false
                onlyAdminsSeeBillableRates shouldBe false
                rounding shouldBe RoundingType.ROUND_UP
                roundingMinutes shouldBe 0
                logoUrl.shouldBeEmpty()
            }
    }

    "getWorkspace should return test user's workspace" {

        val workspace = togglWorkspaceClient.getWorkspace(2963000)

        workspace shouldNotBe null
        workspace?.apply {
            id shouldBe 2963000
            name shouldBe "Enormous2calm4's workspace"
            premium shouldBe false
            admin shouldBe true
            defaultHourlyRate shouldBe 0.0
            defaultCurrency shouldBe "USD"
            onlyAdminsMayCreateProjects shouldBe false
            onlyAdminsSeeBillableRates shouldBe false
            rounding shouldBe RoundingType.ROUND_UP
            roundingMinutes shouldBe 0
            logoUrl.shouldBeEmpty()
        }
    }

    "updateWorkspace should correct update currency" {

        val oldName = "Enormous2calm4's workspace"
        val newName = "Enormous2calm4's new"

        val workspace = togglWorkspaceClient.updateWorkspace(2963000, WorkspaceData(name = newName))

        workspace.apply {
            name shouldBe newName
        }

        val workspaceBack = togglWorkspaceClient.updateWorkspace(2963000, WorkspaceData(name = oldName))

        workspaceBack.apply {
            name shouldBe oldName
        }
    }
})