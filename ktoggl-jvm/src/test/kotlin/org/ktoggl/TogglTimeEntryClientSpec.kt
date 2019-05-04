package org.ktoggl

import io.kotlintest.assertSoftly
import io.kotlintest.be
import io.kotlintest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.matchers.collections.shouldNotBeEmpty
import io.kotlintest.matchers.date.after
import io.kotlintest.matchers.date.before
import io.kotlintest.matchers.numerics.shouldBeGreaterThanOrEqual
import io.kotlintest.matchers.numerics.shouldBeInRange
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec
import org.ktoggl.entity.CreateTimeEntryData
import org.ktoggl.entity.ProjectParent
import org.ktoggl.entity.StartTimeEntryData
import org.ktoggl.entity.UpdateTimeEntryData
import org.ktoggl.jvm.JvmTogglClientBuilder
import org.ktoggl.jvm.entity.endTime
import org.ktoggl.jvm.entity.getTimeEntriesStartedInRange
import org.ktoggl.jvm.entity.startTime
import java.time.OffsetDateTime

class TogglTimeEntryClientSpec : StringSpec({

    val togglTimeEntryClient: TogglTimeEntryClient = JvmTogglClientBuilder().build(ApiToken.value)

    "createTimeEntry should create time entry" {

        val createTimeEntryData = CreateTimeEntryData(
            startTimestamp = 1483274096, endTimestamp = 1483275096,
            parent = ProjectParent(140214510))

        val timeEntry = togglTimeEntryClient.createTimeEntry(createTimeEntryData)
        timeEntry.apply {
            assertSoftly {
                id shouldNotBe null
                workspaceId shouldBe 2963000
                projectId shouldBe 140214510
                taskId shouldBe null
                description shouldBe null
                billable shouldBe false
                startTimestamp shouldBe 1483274096
                endTimestamp shouldBe 1483275096
                startTime shouldBe OffsetDateTime.parse("2017-01-01T12:34:56Z")
                endTime shouldBe OffsetDateTime.parse("2017-01-01T12:51:36Z")
                durationSeconds shouldBe 1000
                tags shouldBe emptyList()
            }
        }
    }

    "createTimeEntry should create time entry with tag and description" {

        val createTimeEntryData = CreateTimeEntryData(
            startTimestamp = 1483274096, endTimestamp = 1483275096,
            parent = ProjectParent(140214541),
            description = "createTimeEntry should create time entry with tag and description",
            tags = listOf("abc"))

        val timeEntry = togglTimeEntryClient.createTimeEntry(createTimeEntryData)
        timeEntry.apply {
            assertSoftly {
                id shouldNotBe null
                workspaceId shouldBe 2963000
                projectId shouldBe 140214541
                taskId shouldBe null
                description shouldBe "createTimeEntry should create time entry with tag and description"
                billable shouldBe false
                startTimestamp shouldBe 1483274096
                endTimestamp shouldBe 1483275096
                startTime shouldBe OffsetDateTime.parse("2017-01-01T12:34:56Z")
                endTime shouldBe OffsetDateTime.parse("2017-01-01T12:51:36Z")
                durationSeconds shouldBe 1000
                tags shouldBe listOf("abc")
            }
        }
    }

    "startTimeEntry should create time entry" {

        val currentTime = OffsetDateTime.now().minusSeconds(1)
        val startTimeEntryData = StartTimeEntryData(
            parent = ProjectParent(140214570),
            tags = listOf("test"),
            description = "startTimeEntry should create time entry")

        val timeEntry = togglTimeEntryClient.startTimeEntry(startTimeEntryData)
        timeEntry.apply {
            assertSoftly {
                id shouldNotBe null
                workspaceId shouldBe 2963000
                projectId shouldBe 140214570
                taskId shouldBe null
                description shouldBe "startTimeEntry should create time entry"
                billable shouldBe false
                startTimestamp shouldBeGreaterThanOrEqual currentTime.toEpochSecond()
                endTimestamp shouldBe null
                durationSeconds shouldBe -startTimestamp
                startTime should (be(currentTime) or after(currentTime))
                endTime shouldBe null
                tags shouldBe listOf("test")
            }
        }

        togglTimeEntryClient.stopTimeEntry(timeEntry.id)
    }

    "!createTimeEntry without endTime then stopTimeEntry should set endTime" {

        val createTimeEntryData = CreateTimeEntryData(
            startTimestamp = 1545568770,
            parent = ProjectParent(140214602),
            description = "createTimeEntry without endTime then stopTimeEntry should set endTime")
        val createdTimeEntry = togglTimeEntryClient.createTimeEntry(createTimeEntryData)

        val timeBeforeStop = OffsetDateTime.now().minusSeconds(1)
        val timeEntry = togglTimeEntryClient.stopTimeEntry(createdTimeEntry.id)
        val timeAfterStop = OffsetDateTime.now().plusSeconds(1)

        timeEntry.apply {
            assertSoftly {
                id shouldBe createdTimeEntry.id
                workspaceId shouldBe 2963000
                projectId shouldBe 140214602
                startTimestamp shouldBe 1545568770
                endTimestamp!! shouldBeInRange (LongRange(timeBeforeStop.toEpochSecond(), timeAfterStop.toEpochSecond()))
                startTime shouldBe OffsetDateTime.parse("2018-12-23T12:39:30Z")
                endTime!! shouldBe (after(timeBeforeStop) and before(timeAfterStop))
            }
        }
    }

    "startTimeEntry and stopTimeEntry sequence should be executable" {

        val startTimeEntryData = StartTimeEntryData(
            parent = ProjectParent(140214627),
            description = "startTimeEntry and stopTimeEntry sequence should be executable")
        val startedTimeEntry = togglTimeEntryClient.startTimeEntry(startTimeEntryData)

        val timeBeforeStop = OffsetDateTime.now().minusSeconds(1)
        val timeEntry = togglTimeEntryClient.stopTimeEntry(startedTimeEntry.id)
        val timeAfterStop = OffsetDateTime.now().plusSeconds(1)

        timeEntry.apply {
            assertSoftly {
                id shouldBe startedTimeEntry.id
                workspaceId shouldBe 2963000
                projectId shouldBe 140214627
                endTimestamp!! shouldBeInRange (LongRange(timeBeforeStop.toEpochSecond(), timeAfterStop.toEpochSecond()))
                endTime!! shouldBe (after(timeBeforeStop) and before(timeAfterStop))
            }
        }
    }

    "getTimeEntry should return valid object" {

        val timeEntry = togglTimeEntryClient.getTimeEntry(1060627344L)
        timeEntry!!.apply {
            assertSoftly {
                id shouldBe 1060627344L
                workspaceId shouldBe 2963000
                projectId shouldBe 140214510
                taskId shouldBe null
                description shouldBe "test time entry"
                billable shouldBe false
                startTimestamp shouldBe 1483274096
                endTimestamp shouldBe 1483275096
                startTime shouldBe OffsetDateTime.parse("2017-01-01T12:34:56Z")
                endTime shouldBe OffsetDateTime.parse("2017-01-01T12:51:36Z")
                durationSeconds shouldBe 1000
                tags shouldBe listOf("abc", "test")
            }
        }
    }

    "getCurrentTimeEntry should return previously started time entry" {

        val startTimeEntryData = StartTimeEntryData(
            parent = ProjectParent(140214541),
            description = "getCurrentTimeEntry should return previously started time entry")
        val startedTimeEntry = togglTimeEntryClient.startTimeEntry(startTimeEntryData)

        val timeEntry = togglTimeEntryClient.getCurrentTimeEntry()

        timeEntry!! shouldBe startedTimeEntry

        togglTimeEntryClient.stopTimeEntry(startedTimeEntry.id)
    }

    "getCurrentTimeEntry should return null if there is no running time entry" {

        val timeEntry = togglTimeEntryClient.getCurrentTimeEntry()

        timeEntry shouldBe null
    }

    "updateTimeEntry should change all properties" {

        val createTimeEntryData = CreateTimeEntryData(
            parent = ProjectParent(140214602),
            description = "some description",
            startTimestamp = 1545568770,
            endTimestamp = 1545569770,
            tags = listOf("test"))
        val createdTimeEntry = togglTimeEntryClient.createTimeEntry(createTimeEntryData)

        val updateTimeEntryData = UpdateTimeEntryData(
            parent = ProjectParent(140214627),
            description = "updateTimeEntry should change all properties",
            startTimestamp = 1545557770,
            endTimestamp = 1545559770,
            tags = listOf("abc"))

        val timeEntry = togglTimeEntryClient.updateTimeEntry(createdTimeEntry.id, updateTimeEntryData)

        timeEntry.apply {
            assertSoftly {
                id shouldBe createdTimeEntry.id
                workspaceId shouldBe 2963000
                projectId shouldBe 140214627
                taskId shouldBe null
                description shouldBe "updateTimeEntry should change all properties"
                startTimestamp shouldBe 1545557770
                endTimestamp!! shouldBe 1545559770
                durationSeconds shouldBe 2000
                startTime shouldBe OffsetDateTime.parse("2018-12-23T09:36:10Z")
                endTime!! shouldBe OffsetDateTime.parse("2018-12-23T10:09:30Z")
                tags shouldBe listOf("abc")
            }
        }
    }

    "deleteTimeEntry should remove object" {

        val createTimeEntryData = CreateTimeEntryData(
            parent = ProjectParent(140214657),
            description = "deleteTimeEntry should remove object",
            startTimestamp = 1545568770,
            endTimestamp = 1545569770)
        val createdTimeEntry = togglTimeEntryClient.createTimeEntry(createTimeEntryData)

        val deletedStatus = togglTimeEntryClient.deleteTimeEntry(createdTimeEntry.id)
        deletedStatus shouldBe true
    }

    "getTimeEntriesStartedInRange should return at least one object" {

        val timeEntries = togglTimeEntryClient.getTimeEntriesStartedInRange(1545568770, 1545579770)
        timeEntries.shouldNotBeEmpty()
    }

    "getTimeEntriesStartedInRange should work with OffsetDateTime" {

        val timeEntries = togglTimeEntryClient.getTimeEntriesStartedInRange(1545568770, 1545579770)
        val timeEntriesFromOffsetDateTime = togglTimeEntryClient.getTimeEntriesStartedInRange(
            OffsetDateTime.parse("2018-12-23T12:39:30Z"),
            OffsetDateTime.parse("2018-12-23T15:42:50Z"))

        timeEntriesFromOffsetDateTime shouldBe timeEntries
    }

    "updateTimeEntriesTags with 'add' action should add suitable tags" {

        val createTimeEntryData1 = CreateTimeEntryData(
            parent = ProjectParent(140214541),
            description = "updateTimeEntriesTags with 'add' action should add suitable tags",
            startTimestamp = 1545538770,
            endTimestamp = 1545539770,
            tags = emptyList())
        val createdTimeEntry1 = togglTimeEntryClient.createTimeEntry(createTimeEntryData1)

        val createTimeEntryData2 = CreateTimeEntryData(
            parent = ProjectParent(140214541),
            description = "updateTimeEntriesTags with 'add' action should add suitable tags",
            startTimestamp = 1545548770,
            endTimestamp = 1545549770,
            tags = listOf("abc"))
        val createdTimeEntry2 = togglTimeEntryClient.createTimeEntry(createTimeEntryData2)

        val updatedTimeEntries = togglTimeEntryClient.updateTimeEntriesTags(
            listOf(createdTimeEntry1.id, createdTimeEntry2.id),
            listOf("test"),
            TogglTimeEntryClient.UpdateTagsAction.ADD)

        updatedTimeEntries shouldHaveSize 2

        updatedTimeEntries.find { it.id == createdTimeEntry1.id }!!
            .apply {
                tags shouldContainExactlyInAnyOrder listOf("test")
            }

        updatedTimeEntries.find { it.id == createdTimeEntry2.id }!!
            .apply {
                tags shouldContainExactlyInAnyOrder listOf("abc", "test")
            }
    }

    "updateTimeEntriesTags with 'remove' action should add suitable tags" {

        val createTimeEntryData1 = CreateTimeEntryData(
            parent = ProjectParent(140214510),
            description = "updateTimeEntriesTags with 'remove' action should add suitable tags",
            startTimestamp = 1545536770,
            endTimestamp = 1545537770,
            tags = listOf("test"))
        val createdTimeEntry1 = togglTimeEntryClient.createTimeEntry(createTimeEntryData1)

        val createTimeEntryData2 = CreateTimeEntryData(
            parent = ProjectParent(140214510),
            description = "updateTimeEntriesTags with 'remove' action should add suitable tags",
            startTimestamp = 1545546770,
            endTimestamp = 1545547770,
            tags = listOf("test", "abc"))
        val createdTimeEntry2 = togglTimeEntryClient.createTimeEntry(createTimeEntryData2)

        val updatedTimeEntries = togglTimeEntryClient.updateTimeEntriesTags(
            listOf(createdTimeEntry1.id, createdTimeEntry2.id),
            listOf("test"),
            TogglTimeEntryClient.UpdateTagsAction.REMOVE)

        updatedTimeEntries shouldHaveSize 2
        updatedTimeEntries.find { it.id == createdTimeEntry1.id }!!
            .apply {
                tags shouldBe emptyList()
            }
        updatedTimeEntries.find { it.id == createdTimeEntry2.id }!!
            .apply {
                tags shouldBe listOf("abc")
            }
    }

    "updateTimeEntriesTags with 'override' action should add suitable tags" {

        val createTimeEntryData1 = CreateTimeEntryData(
            parent = ProjectParent(140214570),
            description = "updateTimeEntriesTags with 'override' action should add suitable tags",
            startTimestamp = 1545516770,
            endTimestamp = 1545527770,
            tags = listOf("test"))
        val createdTimeEntry1 = togglTimeEntryClient.createTimeEntry(createTimeEntryData1)

        val createTimeEntryData2 = CreateTimeEntryData(
            parent = ProjectParent(140214570),
            description = "updateTimeEntriesTags with 'override' action should add suitable tags",
            startTimestamp = 1545526770,
            endTimestamp = 1545537770,
            tags = listOf("test", "abc"))
        val createdTimeEntry2 = togglTimeEntryClient.createTimeEntry(createTimeEntryData2)

        val updatedTimeEntries = togglTimeEntryClient.updateTimeEntriesTags(
            listOf(createdTimeEntry1.id, createdTimeEntry2.id),
            listOf("abc"),
            TogglTimeEntryClient.UpdateTagsAction.OVERRIDE)

        updatedTimeEntries shouldHaveSize 2
        updatedTimeEntries.forEach {
            it.apply {
                tags shouldBe listOf("abc")
            }
        }
    }
})
