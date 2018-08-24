package org.obywatel.ktoggl.internal

import org.obywatel.ktoggl.TogglUserClient
import org.obywatel.ktoggl.entity.User
import org.obywatel.ktoggl.internal.retrofit.TogglApi

internal class TogglUserClientImpl(private val togglApi: TogglApi) : org.obywatel.ktoggl.TogglUserClient {

    override fun getCurrentUser(): User? = togglApi.me().execute().body()?.user?.toExternal()
}