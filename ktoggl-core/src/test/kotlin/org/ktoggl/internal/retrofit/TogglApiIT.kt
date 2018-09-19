//package org.ktoggl.internal.retrofit
//
//import org.amshove.kluent.`should equal`
//import org.amshove.kluent.`should not be null`
//import org.junit.jupiter.api.Test
//
//class TogglApiIT {
//
//    private val apiToken = "0723f331421f3860603b497ba01f790d"
//    private val togglApi: TogglApi = RetrofitFactory.create<TogglApi>(apiToken, "https://www.toggl.com/api/v8/")
//
//    @Test
//    fun `Get me should return test user`() {
//
//        val userResponse = togglApi.getMe().execute().body()
//
//        userResponse.`should not be null`()
//            .user.also {
//            it.id `should equal` 4343480L
//            it.api_token `should equal` apiToken
//            it.default_wid `should equal` 2963000L
//            it.email `should equal` "enormous2calm4@gamil.com"
//            it.fullname `should equal` "Enormous2calm4"
//            it.jquery_timeofday_format `should equal` "h:i A"
//            it.jquery_date_format `should equal` "m/d/Y"
//            it.timeofday_format `should equal` "h:mm A"
//            it.date_format `should equal` "MM/DD/YYYY"
//            it.store_start_and_stop_time `should equal` true
//            it.beginning_of_week `should equal` 1
//            it.language `should equal` "en_US"
//            it.image_url `should equal` "https://assets.toggl.com/avatars/5bd169bb81845e31568fa773b404d660.png"
//            it.sidebar_piechart `should equal` true
//            it.created_at `should equal` "2018-09-16T15:40:54+00:00"
//            it.send_product_emails `should equal` true
//            it.send_weekly_report `should equal` true
//            it.send_timer_notifications `should equal` true
//            it.openid_enabled `should equal` false
//            it.timezone `should equal` "Europe/Warsaw"
//        }
//    }
//}