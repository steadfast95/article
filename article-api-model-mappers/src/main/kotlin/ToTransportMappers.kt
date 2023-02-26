import exceptions.UnknownArticleAction
import models.Article
import models.ArticleAction
import models.ArticleAuthorId
import models.ArticleId
import models.ArticleInnerError
import models.ArticleInnerVisibility
import models.ArticleState
import models.Comment
import ru.otuskotlin.public.article.api.v1.models.ArticleBlockResponse
import ru.otuskotlin.public.article.api.v1.models.ArticleComment
import ru.otuskotlin.public.article.api.v1.models.ArticleCommentResponse
import ru.otuskotlin.public.article.api.v1.models.ArticleCreateResponse
import ru.otuskotlin.public.article.api.v1.models.ArticleError
import ru.otuskotlin.public.article.api.v1.models.ArticleReadResponse
import ru.otuskotlin.public.article.api.v1.models.ArticleResponseObject
import ru.otuskotlin.public.article.api.v1.models.ArticleSearchResponse
import ru.otuskotlin.public.article.api.v1.models.ArticleUpdateResponse
import ru.otuskotlin.public.article.api.v1.models.ArticleVisibility
import ru.otuskotlin.public.article.api.v1.models.IResponse
import ru.otuskotlin.public.article.api.v1.models.ResponseResult

fun ArticleContext.toTransportArticle(): IResponse = when (val action = action) {
    ArticleAction.CREATE -> toTransportCreate()
    ArticleAction.READ -> toTransportRead()
    ArticleAction.UPDATE -> toTransportUpdate()
    ArticleAction.BLOCK -> toTransportBlock()
    ArticleAction.SEARCH -> toTransportSearch()
    ArticleAction.COMMENT -> toTransportComments()
    ArticleAction.NONE -> throw UnknownArticleAction(action)
}

fun ArticleContext.toTransportCreate() = ArticleCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ArticleState.IN_PROGRESS) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    article = articleResponse.toTransportArticle()
)

fun ArticleContext.toTransportRead() = ArticleReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ArticleState.IN_PROGRESS) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    article = articleResponse.toTransportArticle()
)

fun ArticleContext.toTransportUpdate() = ArticleUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ArticleState.IN_PROGRESS) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    article = articleResponse.toTransportArticle()
)

fun ArticleContext.toTransportBlock() = ArticleBlockResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ArticleState.IN_PROGRESS) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    article = articleResponse.toTransportArticle()
)

fun ArticleContext.toTransportSearch() = ArticleSearchResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ArticleState.IN_PROGRESS) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    articles = articlesResponse.toTransportArticles()
)

fun ArticleContext.toTransportComments() = ArticleCommentResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ArticleState.IN_PROGRESS) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    article = articleResponse.toTransportArticle()
)

fun List<Article>.toTransportArticles(): List<ArticleResponseObject>? = this
    .map { it.toTransportArticle() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun Article.toTransportArticle(): ArticleResponseObject = ArticleResponseObject(
    id = id.takeIf { it != ArticleId.NONE }?.asString(),
    title = title.takeIf { it.isNotBlank() },
    ownerId = ownerId.takeIf { it != ArticleAuthorId.NONE }?.asString(),
    visibility = visibility.toTransportArticle(),
    comments = comments.toTransport(),
    content = text
)

private fun ArticleInnerVisibility.toTransportArticle(): ArticleVisibility? = when (this) {
    ArticleInnerVisibility.VISIBLE_PUBLIC -> ArticleVisibility.PUBLIC
    ArticleInnerVisibility.VISIBLE_TO_GROUP -> ArticleVisibility.REGISTERED_ONLY
    ArticleInnerVisibility.VISIBLE_TO_OWNER -> ArticleVisibility.OWNER_ONLY
    ArticleInnerVisibility.NONE -> null
}

private fun MutableList<Comment>.toTransport() = this
    .map { it.toTransport() }

private fun Comment.toTransport() = ArticleComment(
    commentId = this.id.asString(),
    articleId = this.articleId.asString(),
    authorId = this.ownerId.asString(),
    comment = this.comment
)

private fun List<ArticleInnerError>.toTransportErrors(): List<ArticleError>? = this
    .map { it.toTransportArticle() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun ArticleInnerError.toTransportArticle() = ArticleError(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)