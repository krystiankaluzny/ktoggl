package org.ktoggl.internal

import org.ktoggl.TimeUtilProvider
import org.ktoggl.TogglUserClient
import org.ktoggl.entity.User
import org.ktoggl.internal.retrofit.TogglApi

internal class TogglUserClientImpl(private val p: TimeUtilProvider, private val togglApi: TogglApi) : TogglUserClient {

    override fun getCurrentUser(): User? = togglApi.getMe().execute().body()?.user?.toExternal(p)
}