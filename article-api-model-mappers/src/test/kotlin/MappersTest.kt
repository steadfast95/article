import models.Article
import models.ArticleAction
import models.ArticleAuthorId
import models.ArticleId
import models.ArticleInnerVisibility
import models.ArticleRequestId
import models.ArticleState
import models.Comment
import models.WorkMode
import org.junit.Assert
import org.junit.Test
import ru.otuskotlin.public.article.api.v1.models.ArticleBlockResponse
import ru.otuskotlin.public.article.api.v1.models.ArticleComment
import ru.otuskotlin.public.article.api.v1.models.ArticleCreateObject
import ru.otuskotlin.public.article.api.v1.models.ArticleCreateRequest
import ru.otuskotlin.public.article.api.v1.models.ArticleDebug
import ru.otuskotlin.public.article.api.v1.models.ArticleRequestDebugMode
import ru.otuskotlin.public.article.api.v1.models.ArticleRequestDebugStubs
import ru.otuskotlin.public.article.api.v1.models.ArticleVisibility
import ru.otuskotlin.public.article.api.v1.models.ResponseResult
import stubs.ArticleStubs
import java.util.*

class MappersTest {

    @Test
    fun toModelTest() {
        val content = "1".repeat(10)
        val comments = mutableListOf(
            ArticleComment(comment = "nice content"),
            ArticleComment(comment = "thx for content")
        )
        val request = ArticleCreateRequest(
            requestId = UUID.randomUUID().toString(),
            debug = ArticleDebug(
                mode = ArticleRequestDebugMode.TEST,
                stub = ArticleRequestDebugStubs.SUCCESS,
            ),
            article = ArticleCreateObject(
                title = "Some title",
                visibility = ArticleVisibility.PUBLIC,
                content = content,
                comments = comments
            )
        )
        val context = ArticleContext().also {
            it.fromTransport(request)
        }

        Assert.assertEquals(context.workMode, WorkMode.TEST)
        Assert.assertEquals(context.stubCase, ArticleStubs.SUCCESS)

        Assert.assertEquals(context.articleRequest.title, "Some title")
        Assert.assertEquals(context.articleRequest.text, content)
        Assert.assertTrue(
            context.articleRequest.comments.map { it.comment }.containsAll(comments.map { it.comment })
        )
    }

    @Test
    fun toTransportTest() {
        val context = ArticleContext(
            action = ArticleAction.BLOCK,
            state = ArticleState.IN_PROGRESS,
            errors = mutableListOf(),
            workMode = WorkMode.PROD,
            stubCase = ArticleStubs.SUCCESS,
            requestId = ArticleRequestId(UUID.randomUUID().toString()),
            articleResponse = Article(
                id = ArticleId(UUID.randomUUID().toString()),
                title = "Some title",
                text = "My awesome text",
                ownerId = ArticleAuthorId.NONE,
                comments = mutableListOf(Comment(comment = "my awesome comment")),
                visibility = ArticleInnerVisibility.VISIBLE_TO_GROUP
            )
        )

        val transportArticle = context.toTransportArticle() as ArticleBlockResponse
        Assert.assertTrue(transportArticle.errors.isNullOrEmpty())

        Assert.assertEquals(ResponseResult.SUCCESS, transportArticle.result)
        Assert.assertEquals(context.articleResponse.text, transportArticle.article?.content)
        Assert.assertEquals(context.articleResponse.title, transportArticle.article?.title)
        Assert.assertEquals(context.articleResponse.id.asString(), transportArticle.article?.id)
        Assert.assertEquals(context.requestId.asString(), transportArticle.requestId)
    }
}