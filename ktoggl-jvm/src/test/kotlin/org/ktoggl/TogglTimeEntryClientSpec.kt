package org.ktoggl

import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import org.ktoggl.android.JvmTogglClientBuilder
import org.ktoggl.entity.TimeEntry

class TogglTimeEntryClientSpec : StringSpec({

    val togglTimeEntryClient: TogglTimeEntryClient = JvmTogglClientBuilder().build(ApiToken.value)

    "createTimeEntry with predefined id should throw IllegalArgumentException" {

        val exception = shouldThrow<IllegalArgumentException> {
            togglTimeEntryClient.createTimeEntry(TimeEntry(id = 12))
        }

        exception.message shouldBe "Cannot create time entry with defined id"
    }

    "createTimeEntry without startTimestamp should throw IllegalArgumentException" {

        val exception = shouldThrow<IllegalArgumentException> {
            togglTimeEntryClient.createTimeEntry(TimeEntry())
        }

        exception.message shouldBe "Cannot create time entry without startTimestamp"
    }

    "createTimeEntry without parentId (at least one of the following workspaceId, projectId, taskId) should throw IllegalArgumentException" {

        val exception = shouldThrow<IllegalArgumentException> {
            togglTimeEntryClient.createTimeEntry(TimeEntry(startTimestamp = 1483274096))
        }

        exception.message shouldBe "At least one of the following must be defined: workspaceId, projectId, taskId"
    }

    "createTimeEntry should create time entry" {

        val timeEntry = togglTimeEntryClient.createTimeEntry(TimeEntry(projectId = 140214510, startTimestamp = 1483274096, durationSeconds = 1000))
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
})

data class TimeEntry(
    var id: Long? = null,
    var workspaceId: Long? = null,
    var projectId: Long? = null,
    var taskId: Long? = null,
    var description: String? = null,
    var billable: Boolean? = null,
    var startTimestamp: Long? = null,
    var endTimestamp: Long? = null,
    var durationSeconds: Long? = null,
    var tags: List<String>? = null,
    var lastUpdateTimestamp: Long? = null
)