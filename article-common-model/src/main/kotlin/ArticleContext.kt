import kotlinx.datetime.Instant
import models.Article
import models.ArticleAction
import models.ArticleInnerError
import models.ArticleFilter
import models.ArticleRequestId
import models.ArticleState
import models.WorkMode
import stubs.ArticleStubs

data class ArticleContext(
    var action: ArticleAction = ArticleAction.NONE,
    var state: ArticleState = ArticleState.NONE,
    val errors: MutableList<ArticleInnerError> = mutableListOf(),

    var workMode: WorkMode = WorkMode.PROD,
    var stubCase: ArticleStubs = ArticleStubs.NONE,

    var requestId: ArticleRequestId = ArticleRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var articleRequest: Article = Article(),
    var articleFilterRequest: ArticleFilter = ArticleFilter(),
    var articleResponse: Article = Article(),
    val articlesResponse: MutableList<Article> = mutableListOf(),
)