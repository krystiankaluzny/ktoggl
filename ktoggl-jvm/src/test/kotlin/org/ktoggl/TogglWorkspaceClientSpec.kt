package org.ktoggl

import io.kotlintest.matchers.string.shouldBeEmpty
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.ktoggl.android.JvmTogglClientBuilder
import org.ktoggl.entity.RoundingType

class TogglWorkspaceClientSpec: StringSpec( {

    val apiToken = "0723f331421f3860603b497ba01f790d"
    val togglWorkspaceClient: TogglWorkspaceClient = JvmTogglClientBuilder().build(apiToken)

    "getWorkspaces should return one test user's workspace" {

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
})