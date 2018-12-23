package org.ktoggl

import io.kotlintest.matchers.numerics.shouldBeGreaterThan
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec
import org.ktoggl.android.JvmTogglClientBuilder
import org.ktoggl.entity.CreateTimeEntryData
import org.ktoggl.entity.ProjectParent
import org.ktoggl.entity.StartTimeEntryData
import java.time.Clock

class TogglTimeEntryClientSpec : StringSpec({

    val togglTimeEntryClient: TogglTimeEntryClient = JvmTogglClientBuilder().build(ApiToken.value)

    "createTimeEntry should create time entry" {

        val createTimeEntryData = CreateTimeEntryData(
            startTimestamp = 1483274096, endTimestamp = 1483275096,
            parent = ProjectParent(140214510))

        val timeEntry = togglTimeEntryClient.createTimeEntry(createTimeEntryData)
        timeEntry.apply {
            id shouldNotBe null
            workspaceId shouldBe 2963000
            projectId shouldBe 140214510
            taskId shouldBe null
            description shouldBe null
            billable shouldBe false
            startTimestamp shouldBe 1483274096
            endTimestamp shouldBe 1483275096
            durationSeconds shouldBe 1000
            tags shouldBe emptyList()
        }
    }

    "createTimeEntry should create time entry with tag and description" {

        val createTimeEntryData = CreateTimeEntryData(
            startTimestamp = 1483274096, endTimestamp = 1483275096,
            parent = ProjectParent(140214541),
            description = "Time entry with tag",
            tags = listOf("abc"))

        val timeEntry = togglTimeEntryClient.createTimeEntry(createTimeEntryData)
        timeEntry.apply {
            id shouldNotBe null
            workspaceId shouldBe 2963000
            projectId shouldBe 140214541
            taskId shouldBe null
            description shouldBe "Time entry with tag"
            billable shouldBe false
            startTimestamp shouldBe 1483274096
            endTimestamp shouldBe 1483275096
            durationSeconds shouldBe 1000
            tags shouldBe listOf("abc")
        }
    }

    "startTimeEntry should create time entry" {

        val currentTimeSec = System.currentTimeMillis() / 1000L
        val createTimeEntryData = StartTimeEntryData(parent = ProjectParent(140214570), tags = listOf("test"))

        val timeEntry = togglTimeEntryClient.startTimeEntry(createTimeEntryData)
        timeEntry.apply {
            id shouldNotBe null
            workspaceId shouldBe 2963000
            projectId shouldBe 140214570
            taskId shouldBe null
            description shouldBe null
            billable shouldBe false
            startTimestamp shouldBeGreaterThan currentTimeSec
            endTimestamp shouldBe null
            durationSeconds shouldBe -startTimestamp
            tags shouldBe listOf("test")
        }
    }
})
