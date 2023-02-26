package models

@JvmInline
value class ArticleRequestId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = ArticleRequestId("")
    }
}

enum class WorkMode {
    PROD,
    STUB,
    TEST
}

enum class ArticleState {
    NONE,
    IN_PROGRESS,
    PROCESSING_FINISHED,
    ERROR
}

data class ArticleFilter(
    var searchString: String = "",
    var ownerId: ArticleAuthorId = ArticleAuthorId.NONE,
)