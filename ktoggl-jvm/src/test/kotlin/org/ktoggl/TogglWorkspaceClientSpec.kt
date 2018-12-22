package org.ktoggl

import io.kotlintest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotlintest.matchers.string.shouldBeEmpty
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec
import org.ktoggl.android.JvmTogglClientBuilder
import org.ktoggl.entity.*

class TogglWorkspaceClientSpec : StringSpec({

    val togglWorkspaceClient: TogglWorkspaceClient = JvmTogglClientBuilder().build(ApiToken.value)

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

    "updateWorkspace should correct update workspace's name" {

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

    "getWorkspaceProjects with ProjectStateFilter.ACTIVE should return active projects only" {

        val projects = togglWorkspaceClient.getWorkspaceProjects(2963000, ProjectStateFilter.ACTIVE)

        projects.shouldContainExactlyInAnyOrder(
            Project(id = 140214510, name = "Project 1", workspaceId = 2963000, clientId = null, active = true, private = true, creationTimestamp = 1537112683, colorId = 3, color = -292076),
            Project(id = 140214541, name = "Project 2", workspaceId = 2963000, clientId = null, active = true, private = true, creationTimestamp = 1537112690, colorId = 5, color = -11810816),
            Project(id = 140214570, name = "Project 3", workspaceId = 2963000, clientId = null, active = true, private = true, creationTimestamp = 1537112697, colorId = 7, color = -1992058),
            Project(id = 140214602, name = "Project 4", workspaceId = 2963000, clientId = null, active = true, private = true, creationTimestamp = 1537112704, colorId = 0, color = -16340235),
            Project(id = 140214627, name = "Project 5", workspaceId = 2963000, clientId = null, active = true, private = true, creationTimestamp = 1537112710, colorId = 13, color = -1964795),
            Project(id = 140214657, name = "Project 6", workspaceId = 2963000, clientId = null, active = true, private = true, creationTimestamp = 1537112717, colorId = 3, color = -292076)
        )
    }

    "getWorkspaceProjects with ProjectStateFilter.INACTIVE should return archived projects only" {

        val projects = togglWorkspaceClient.getWorkspaceProjects(2963000, ProjectStateFilter.INACTIVE)

        projects.shouldContainExactlyInAnyOrder(
            Project(id = 144119708, name = "Project 7 inactive", workspaceId = 2963000, clientId = null, active = false, private = true, creationTimestamp = 1538597796, colorId = 12, color = -7798784)
        )
    }

    "getWorkspaceProjects with ProjectStateFilter.ANY should return both active and archived projects" {

        val projects = togglWorkspaceClient.getWorkspaceProjects(2963000, ProjectStateFilter.ANY)

        projects.shouldContainExactlyInAnyOrder(
            Project(id = 140214510, name = "Project 1", workspaceId = 2963000, clientId = null, active = true, private = true, creationTimestamp = 1537112683, colorId = 3, color = -292076),
            Project(id = 140214541, name = "Project 2", workspaceId = 2963000, clientId = null, active = true, private = true, creationTimestamp = 1537112690, colorId = 5, color = -11810816),
            Project(id = 140214570, name = "Project 3", workspaceId = 2963000, clientId = null, active = true, private = true, creationTimestamp = 1537112697, colorId = 7, color = -1992058),
            Project(id = 140214602, name = "Project 4", workspaceId = 2963000, clientId = null, active = true, private = true, creationTimestamp = 1537112704, colorId = 0, color = -16340235),
            Project(id = 140214627, name = "Project 5", workspaceId = 2963000, clientId = null, active = true, private = true, creationTimestamp = 1537112710, colorId = 13, color = -1964795),
            Project(id = 140214657, name = "Project 6", workspaceId = 2963000, clientId = null, active = true, private = true, creationTimestamp = 1537112717, colorId = 3, color = -292076),
            Project(id = 144119708, name = "Project 7 inactive", workspaceId = 2963000, clientId = null, active = false, private = true, creationTimestamp = 1538597796, colorId = 12, color = -7798784)
        )
    }

    "getWorkspaceTags should return all tags" {
        val tags = togglWorkspaceClient.getWorkspaceTags(2963000)

        tags.shouldContainExactlyInAnyOrder(
            Tag(id=4976917, workspaceId=2963000, name="abc"),
            Tag(id=4976916, workspaceId=2963000, name="test")
        )
    }
})