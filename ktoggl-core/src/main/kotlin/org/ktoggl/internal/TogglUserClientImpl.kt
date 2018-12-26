package org.ktoggl.internal

import org.ktoggl.TimeUtilProvider
import org.ktoggl.TogglUserClient
import org.ktoggl.entity.User
import org.ktoggl.entity.UserData
import org.ktoggl.entity.UserPassword
import org.ktoggl.internal.retrofit.TogglApi
import org.ktoggl.internal.retrofit.dto.UserUpdateRequest

internal class TogglUserClientImpl(private val p: TimeUtilProvider, private val togglApi: TogglApi) : TogglUserClient {
    override fun getCurrentUser(): User = togglApi.getMe().execute().body()!!.user.toExternal(p)

    override fun updateCurrentUser(userData: UserData): User {
        val execute = togglApi.updateMe(UserUpdateRequest(userData.toInternal())).execute()
        return execute.body()!!.user.toExternal(p)
    }

    override fun updateCurrentUserPassword(userPassword: UserPassword): User {
        val execute = togglApi.updateMe(UserUpdateRequest(userPassword.toInternal())).execute()
        return execute.body()!!.user.toExternal(p)
    }
}