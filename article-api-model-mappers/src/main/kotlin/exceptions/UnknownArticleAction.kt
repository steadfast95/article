package exceptions

import models.ArticleAction

class UnknownArticleAction(action: ArticleAction) : Throwable("Wrong action $action at mapping toTransport stage")
