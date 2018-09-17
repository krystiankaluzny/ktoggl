package org.ktoggl

import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldEqualTo
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.Test
import org.ktoggl.android.JvmTogglClientBuilder
import org.ktoggl.entity.Day

class TogglUserClientIT {

    private val apiToken = "0723f331421f3860603b497ba01f790d"
    private val togglUserClient: TogglUserClient = JvmTogglClientBuilder().build(apiToken)

    @Test
    fun `getCurrentUser should return test user`() {

        val user = togglUserClient.getCurrentUser()

        user.shouldNotBeNull()
            .also {
                it.id shouldEqualTo 4343480L
                it.apiToken shouldBeEqualTo apiToken
                it.defaultWorkspaceId shouldEqualTo 2963000L
                it.fullName shouldBeEqualTo "Enormous2calm4"
                it.email shouldBeEqualTo  "enormous2calm4@gamil.com"
                it.beginningOfWeek shouldBe Day.MONDAY
                it.language shouldBeEqualTo "en_US"
                it.imageUrl shouldBeEqualTo "https://assets.toggl.com/avatars/5bd169bb81845e31568fa773b404d660.png"
                it.creationTimestamp shouldEqualTo 1537112454
                it.timezone shouldBeEqualTo "Europe/Warsaw"
            }
    }
}