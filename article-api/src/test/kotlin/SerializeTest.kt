import org.junit.Test
import ru.otuskotlin.public.article.api.v1.models.ArticleComment
import ru.otuskotlin.public.article.api.v1.models.ArticleCreateObject
import ru.otuskotlin.public.article.api.v1.models.ArticleCreateRequest
import ru.otuskotlin.public.article.api.v1.models.ArticleCreateResponse
import ru.otuskotlin.public.article.api.v1.models.ArticleResponseObject
import ru.otuskotlin.public.article.api.v1.models.ArticleVisibility
import ru.otuskotlin.public.article.api.v1.models.RequestType
import ru.otuskotlin.public.article.api.v1.models.ResponseResult
import java.util.*
import kotlin.random.Random

import kotlin.test.assertContains

class SerializeTest {

    @Test
    fun serializeRequest() {
        val createRequest = ArticleCreateRequest(
            requestId = UUID.randomUUID().toString(),
            article = ArticleCreateObject(
                title = "test title",
                visibility = ArticleVisibility.PUBLIC,
                content = "1".repeat(10),
                comments = emptyList(),
            )
        )

        val json = articleMapper.writeValueAsString(createRequest)

        assertContains(json, Regex("\"title\":\\s*\"test title\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
        assertContains(json, Regex("\"visibility\":\\s*\"public\""))
    }

    @Test
    fun serializeResponse() {
        val createResponse = ArticleCreateResponse(
            responseType = RequestType.CREATE,
            requestId = UUID.randomUUID().toString(),
            result = ResponseResult.SUCCESS,
            errors = emptyList(),
            article = ArticleResponseObject(
                title = "test title",
                visibility = ArticleVisibility.REGISTERED_ONLY,
                content = "2".repeat(5),
                comments = listOf(ArticleComment(comment = "some comment")),
                id = Random.nextLong().toString(),
            )
        )
        val json = articleMapper.writeValueAsString(createResponse)

        assertContains(json, Regex("\"title\":\\s*\"test title\""))
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
        assertContains(json, Regex("\"visibility\":\\s*\"registeredOnly\""))
    }
}