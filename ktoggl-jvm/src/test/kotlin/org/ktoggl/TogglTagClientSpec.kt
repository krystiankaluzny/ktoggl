package org.ktoggl

import io.kotlintest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec
import org.ktoggl.jvm.JvmTogglClientBuilder
import org.ktoggl.entity.Tag

class TogglTagClientSpec : StringSpec({

    val togglTagClient: TogglTagClient = JvmTogglClientBuilder().build(ApiToken.value)
    val togglWorkspaceClient: TogglWorkspaceClient = JvmTogglClientBuilder().build(ApiToken.value)

    "createTag, deleteTag sequence should be executable and list of all tags should not changed" {

        val tagCreated = togglTagClient.createTag(2963000, "test_tag")
        tagCreated.apply {
            id shouldNotBe null
            workspaceId shouldBe 2963000
            name shouldBe "test_tag"
        }

        val deletedStatus = togglTagClient.deleteTag(tagCreated.id)
        deletedStatus shouldBe true

        val tags = togglWorkspaceClient.getWorkspaceTags(2963000)
        tags.shouldContainExactlyInAnyOrder(
            Tag(id = 4976917, workspaceId = 2963000, name = "abc"),
            Tag(id = 4976916, workspaceId = 2963000, name = "test")
        )
    }

    "updateTag should correct update tag's name" {

        val oldName = "test"
        val newName = "test new"

        val tag = togglTagClient.updateTag(4976916, newName)
        tag.apply {
            id shouldBe 4976916
            workspaceId shouldBe 2963000
            name shouldBe newName
        }

        val tagBack = togglTagClient.updateTag(4976916, oldName)
        tagBack.apply {
            id shouldBe 4976916
            workspaceId shouldBe 2963000
            name shouldBe oldName
        }
    }

    "!deleteTag in case of create or update tag failure" {
        val deletedStatus = togglTagClient.deleteTag(5339024)
        deletedStatus shouldBe true
    }

})