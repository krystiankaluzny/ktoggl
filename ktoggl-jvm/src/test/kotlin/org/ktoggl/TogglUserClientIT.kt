package org.ktoggl

import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.Test
import org.ktoggl.android.JvmTogglClientBuilder
import org.ktoggl.entity.Day
import org.ktoggl.entity.UserData
import org.ktoggl.jvm.entity.creationTime
import java.time.OffsetDateTime

class TogglUserClientIT {

    private val apiToken = "0723f331421f3860603b497ba01f790d"
    private val togglUserClient: TogglUserClient = JvmTogglClientBuilder().build(apiToken)

    @Test
    fun `getCurrentUser should return test user`() {

        val user = togglUserClient.getCurrentUser()

        user.apply {
            id shouldEqualTo 4343480L
            apiToken shouldBeEqualTo apiToken
            defaultWorkspaceId shouldEqualTo 2963000L
            fullName shouldBeEqualTo "Enormous2calm4"
            email shouldBeEqualTo "enormous2calm4@gamil.com"
            beginningOfWeek shouldBe Day.MONDAY
            language shouldBeEqualTo "en_US"
            imageUrl shouldBeEqualTo "https://assets.toggl.com/avatars/5bd169bb81845e31568fa773b404d660.png"
            creationTimestamp shouldEqualTo 1537112454
            creationTime shouldEqual OffsetDateTime.parse("2018-09-16T15:40:54Z")
            timezone shouldBeEqualTo "Europe/Warsaw"
        }
    }

    @Test
    fun `updateCurrentUser with new full name and try update it back`() {

        val oldFullName = "Enormous2calm4"
        val newFullName = "NewFullName"

        val user = togglUserClient.updateCurrentUser(UserData(fullName = newFullName))

        user.apply {
            fullName shouldBeEqualTo newFullName
        }

        val userBack = togglUserClient.updateCurrentUser(UserData(fullName = oldFullName))

        userBack.apply {
            fullName shouldBeEqualTo oldFullName
        }
    }
}