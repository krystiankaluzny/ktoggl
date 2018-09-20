package org.ktoggl.internal.retrofit

import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec


class TogglApiSpec : StringSpec({

    val apiToken = "0723f331421f3860603b497ba01f790d"
    val togglApi: TogglApi = RetrofitFactory.create<TogglApi>(apiToken, "https://www.toggl.com/api/v8/")

    "Get me should return test user" {

        val userResponse = togglApi.getMe().execute().body()

        userResponse shouldNotBe null
        userResponse!!.user.apply {
            id shouldBe 4343480L
            api_token shouldBe apiToken
            default_wid shouldBe 2963000L
            email shouldBe "enormous2calm4@gamil.com"
            fullname shouldBe "Enormous2calm4"
            jquery_timeofday_format shouldBe "h:i A"
            jquery_date_format shouldBe "m/d/Y"
            timeofday_format shouldBe "h:mm A"
            date_format shouldBe "MM/DD/YYYY"
            store_start_and_stop_time shouldBe true
            beginning_of_week shouldBe 1.toByte()
            language shouldBe "en_US"
            image_url shouldBe "https://assets.toggl.com/avatars/5bd169bb81845e31568fa773b404d660.png"
            sidebar_piechart shouldBe true
            created_at shouldBe "2018-09-16T15:40:54+00:00"
            send_product_emails shouldBe true
            send_weekly_report shouldBe true
            send_timer_notifications shouldBe true
            openid_enabled shouldBe false
            timezone shouldBe "Europe/Warsaw"
        }
    }
})