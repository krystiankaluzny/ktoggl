package org.obywatel.ktoggl.internal

import org.obywatel.ktoggl.TimeUtilProvider
import org.obywatel.ktoggl.TogglUserClient
import org.obywatel.ktoggl.entity.User
import org.obywatel.ktoggl.internal.retrofit.TogglApi

internal class TogglUserClientImpl(private val p: TimeUtilProvider, private val togglApi: TogglApi) : TogglUserClient {

    override fun getCurrentUser(): User? = togglApi.me().execute().body()?.user?.toExternal(p)
}