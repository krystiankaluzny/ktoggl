package org.ktoggl

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.ktoggl.android.JvmTogglClientBuilder
import org.ktoggl.entity.Day
import org.ktoggl.entity.UserData
import org.ktoggl.entity.UserPassword
import org.ktoggl.jvm.entity.creationTime
import java.time.OffsetDateTime

class TogglUserClientSpec : StringSpec({

    val togglUserClient: TogglUserClient = JvmTogglClientBuilder().build(ApiToken.value)

    "getCurrentUser should return test user" {

        val user = togglUserClient.getCurrentUser()

        user.apply {
            id shouldBe 4343480L
            apiToken shouldBe apiToken
            defaultWorkspaceId shouldBe 2963000L
            fullName shouldBe "Enormous2calm4"
            email shouldBe "enormous2calm4@gamil.com"
            beginningOfWeek shouldBe Day.MONDAY
            language shouldBe "en_US"
            imageUrl shouldBe "https://assets.toggl.com/avatars/5bd169bb81845e31568fa773b404d660.png"
            creationTimestamp shouldBe 1537112454
            creationTime shouldBe OffsetDateTime.parse("2018-09-16T15:40:54Z")
            timezone shouldBe "Europe/Warsaw"
        }
    }

    "updateCurrentUser with new full name and try update it back" {

        val oldFullName = "Enormous2calm4"
        val newFullName = "NewFullName"

        val user = togglUserClient.updateCurrentUser(UserData(fullName = newFullName))

        user.apply {
            fullName shouldBe newFullName
        }

        val userBack = togglUserClient.updateCurrentUser(UserData(fullName = oldFullName))

        userBack.apply {
            fullName shouldBe oldFullName
        }
    }

    // "We don't want expose and update password every time when tests run"
    "!updateCurrentUserPassword should work" {

        //this isn't real password, trust me
        val userPassword = UserPassword("dfvgh5467", "dfvgh5467XX")
        val user = togglUserClient.updateCurrentUserPassword(userPassword)

        user.apply {
            apiToken shouldBe apiToken
        }
    }
})