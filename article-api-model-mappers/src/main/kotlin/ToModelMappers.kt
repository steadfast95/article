import exceptions.UnknownRequestClass
import models.Article
import models.ArticleAction
import models.ArticleAuthorId
import models.ArticleCommentId
import models.ArticleFilter
import models.ArticleId
import models.ArticleInnerVisibility
import models.ArticleRequestId
import models.Comment
import models.WorkMode
import ru.otuskotlin.public.article.api.v1.models.ArticleBlockRequest
import ru.otuskotlin.public.article.api.v1.models.ArticleComment
import ru.otuskotlin.public.article.api.v1.models.ArticleCommentRequest
import ru.otuskotlin.public.article.api.v1.models.ArticleCreateObject
import ru.otuskotlin.public.article.api.v1.models.ArticleCreateRequest
import ru.otuskotlin.public.article.api.v1.models.ArticleDebug
import ru.otuskotlin.public.article.api.v1.models.ArticleReadRequest
import ru.otuskotlin.public.article.api.v1.models.ArticleRequestDebugMode
import ru.otuskotlin.public.article.api.v1.models.ArticleRequestDebugStubs
import ru.otuskotlin.public.article.api.v1.models.ArticleSearchFilter
import ru.otuskotlin.public.article.api.v1.models.ArticleSearchRequest
import ru.otuskotlin.public.article.api.v1.models.ArticleUpdateObject
import ru.otuskotlin.public.article.api.v1.models.ArticleUpdateRequest
import ru.otuskotlin.public.article.api.v1.models.ArticleVisibility
import ru.otuskotlin.public.article.api.v1.models.IRequest
import stubs.ArticleStubs

fun ArticleContext.fromTransport(request: IRequest) = when (request) {
    is ArticleCreateRequest -> fromTransport(request)
    is ArticleReadRequest -> fromTransport(request)
    is ArticleUpdateRequest -> fromTransport(request)
    is ArticleBlockRequest -> fromTransport(request)
    is ArticleSearchRequest -> fromTransport(request)
    is ArticleCommentRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun String?.toArticleId() = this?.let { ArticleId(it) } ?: ArticleId.NONE
private fun String?.toArticleCommentId() = this?.let { ArticleCommentId(it) } ?: ArticleCommentId.NONE
private fun String?.toArticleAuthorId() = this?.let { ArticleAuthorId(it) } ?: ArticleAuthorId.NONE
private fun String?.toAdWithId() = Article(id = this.toArticleId())
private fun IRequest?.requestId() = this?.requestId?.let { ArticleRequestId(it) } ?: ArticleRequestId.NONE

private fun ArticleDebug?.transportToWorkMode(): WorkMode = when (this?.mode) {
    ArticleRequestDebugMode.PROD -> WorkMode.PROD
    ArticleRequestDebugMode.TEST -> WorkMode.TEST
    ArticleRequestDebugMode.STUB -> WorkMode.STUB
    null -> WorkMode.PROD
}

private fun ArticleDebug?.transportToStubCase(): ArticleStubs = when (this?.stub) {
    ArticleRequestDebugStubs.SUCCESS -> ArticleStubs.SUCCESS
    ArticleRequestDebugStubs.NOT_FOUND -> ArticleStubs.NOT_FOUND
    ArticleRequestDebugStubs.BAD_ID -> ArticleStubs.BAD_ID
    ArticleRequestDebugStubs.BAD_TITLE -> ArticleStubs.BAD_TITLE
    ArticleRequestDebugStubs.BAD_VISIBILITY -> ArticleStubs.BAD_VISIBILITY
    ArticleRequestDebugStubs.CANNOT_BLOCK -> ArticleStubs.CANNOT_DELETE
    ArticleRequestDebugStubs.BAD_SEARCH_STRING -> ArticleStubs.BAD_SEARCH_STRING
    null -> ArticleStubs.NONE
}


fun ArticleContext.fromTransport(request: ArticleCreateRequest) {
    action = ArticleAction.CREATE
    requestId = request.requestId()
    articleRequest = request.article?.toInternal() ?: Article()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ArticleContext.fromTransport(request: ArticleReadRequest) {
    action = ArticleAction.READ
    requestId = request.requestId()
    articleRequest = request.article?.id.toAdWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ArticleContext.fromTransport(request: ArticleUpdateRequest) {
    action = ArticleAction.UPDATE
    requestId = request.requestId()
    articleRequest = request.article?.toInternal() ?: Article()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ArticleContext.fromTransport(request: ArticleBlockRequest) {
    action = ArticleAction.BLOCK
    requestId = request.requestId()
    articleRequest = request.article?.id.toAdWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ArticleContext.fromTransport(request: ArticleSearchRequest) {
    action = ArticleAction.SEARCH
    requestId = request.requestId()
    articleFilterRequest = request.articleFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ArticleContext.fromTransport(request: ArticleCommentRequest) {
    action = ArticleAction.COMMENT
    requestId = request.requestId()
    articleRequest = request.article?.articleId.toAdWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun ArticleSearchFilter?.toInternal(): ArticleFilter = ArticleFilter(
    searchString = this?.searchString ?: ""
)

private fun ArticleCreateObject.toInternal(): Article = Article(
    title = this.title ?: "",
    visibility = this.visibility.fromTransport(),
    text = this.content ?: "",
    comments = this.comments.toComments()
)


private fun ArticleComment.toComment() = Comment(
    id = this.commentId.toArticleCommentId(),
    articleId = this.articleId.toArticleId(),
    ownerId = this.authorId.toArticleAuthorId(),
    comment = this.comment ?: ""
)

private fun List<ArticleComment>?.toComments() = (this ?: emptyList())
    .map { it.toComment() }
    .toMutableList()


private fun ArticleUpdateObject.toInternal(): Article = Article(
    id = this.id.toArticleId(),
    title = this.title ?: "",
    visibility = this.visibility.fromTransport(),
)

private fun ArticleVisibility?.fromTransport(): ArticleInnerVisibility = when (this) {
    ArticleVisibility.PUBLIC -> ArticleInnerVisibility.VISIBLE_PUBLIC
    ArticleVisibility.OWNER_ONLY -> ArticleInnerVisibility.VISIBLE_TO_OWNER
    ArticleVisibility.REGISTERED_ONLY -> ArticleInnerVisibility.VISIBLE_TO_GROUP
    null -> ArticleInnerVisibility.NONE
}