package models

data class Article(
    var id: ArticleId = ArticleId.NONE,
    var title: String = "",
    var text: String = "",
    var ownerId: ArticleAuthorId = ArticleAuthorId.NONE,
    var comments: MutableList<Comment> = mutableListOf(),
    var visibility: ArticleInnerVisibility = ArticleInnerVisibility.NONE,
)

data class Comment(
    val id: ArticleCommentId = ArticleCommentId.NONE,
    val articleId: ArticleId = ArticleId.NONE,
    var ownerId: ArticleAuthorId = ArticleAuthorId.NONE,
    val comment: String = ""
)

@JvmInline
value class ArticleCommentId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = ArticleCommentId("")
    }
}

@JvmInline
value class ArticleId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = ArticleId("")
    }
}

@JvmInline
value class ArticleAuthorId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = ArticleAuthorId("")
    }
}

enum class ArticleInnerVisibility {
    NONE,
    VISIBLE_TO_OWNER,
    VISIBLE_TO_GROUP,
    VISIBLE_PUBLIC,
}