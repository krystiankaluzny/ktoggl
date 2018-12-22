package org.ktoggl

import io.kotlintest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec
import org.ktoggl.android.JvmTogglClientBuilder
import org.ktoggl.entity.Tag

class TogglTagClientSpec : StringSpec({

    val togglTagClient: TogglTagClient = JvmTogglClientBuilder().build(ApiToken.value)
    val togglWorkspaceClient: TogglWorkspaceClient = JvmTogglClientBuilder().build(ApiToken.value)

    "createTag, updateTag, deleteTag sequence should be executable" {

        val tagCreated = togglTagClient.createTag(2963000, "test_tag3")!!
        tagCreated.apply {
            id shouldNotBe null
            workspaceId shouldBe 2963000
            name shouldBe "test_tag3"
        }

        val tagUpdated = togglTagClient.updateTag(tagCreated.id, "new_test_tag")
        tagUpdated.apply {
            id shouldBe tagCreated.id
            workspaceId shouldBe 2963000
            name shouldBe "new_test_tag"
        }

        val deletedStatus = togglTagClient.deleteTag(tagCreated.id)
        deletedStatus shouldBe true


        val tags = togglWorkspaceClient.getWorkspaceTags(2963000)
        tags.shouldContainExactlyInAnyOrder(
            Tag(id = 4976917, workspaceId = 2963000, name = "abc"),
            Tag(id = 4976916, workspaceId = 2963000, name = "test")
        )
    }

    "!deleteTag in case of create or update tag failure" {
        val deletedStatus = togglTagClient.deleteTag(5339024)
        deletedStatus shouldBe true
    }
})