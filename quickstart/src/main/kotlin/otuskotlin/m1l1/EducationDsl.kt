package otuskotlin.m1l1

@DslMarker
annotation class EducationDsl

data class Education(
    val teacherFirstName: String,
    val teacherLastName: String,
    val teacherSecondName: String?,
    val title: String,
    val duration: String,
    val type: LessonType,
    val links: Set<String>,
)

enum class LessonType {
    OFFLINE, LIVE,SELF
}

@EducationDsl
class TeacherContext {
    var firstName = ""
    var lastName = ""
    var secondName: String? = null
}

@EducationDsl
class LessonContext {
    var title = ""
    var duration = ""
}

@EducationDsl
class LessonTypeContext {
    var type: LessonType = LessonType.LIVE
}

@EducationDsl
class LinksContext {
    private var innerLinks = mutableSetOf<String>()
    val links: Set<String>
        get() = innerLinks.toSet()

    fun add(link: String) {
        innerLinks.add(link)
    }

    operator fun String.unaryPlus() = add(this)
}

@EducationDsl
class EducationContext {
    var teacherFirstName = ""
    var teacherLastName = ""
    var teacherSecondName: String? = null
    var title = ""
    var duration = ""
    var type: LessonType = LessonType.LIVE
    var links = emptySet<String>()

    @EducationDsl
    fun teacher(block: TeacherContext.() -> Unit) {
        val ctx = TeacherContext().apply(block)
        teacherFirstName = ctx.firstName
        teacherLastName = ctx.lastName
        teacherSecondName = ctx.secondName
    }

    @EducationDsl
    fun lesson(block: LessonContext.() -> Unit) {
        val ctx = LessonContext().apply(block)

        title = ctx.title
        duration = ctx.duration
    }

    @EducationDsl
    fun type(block: LessonTypeContext.() -> Unit) {
        val ctx = LessonTypeContext().apply(block)

        type = ctx.type
    }

    @EducationDsl
    fun links(block: LinksContext.() -> Unit) {
        val ctx = LinksContext().apply(block)

        links = ctx.links
    }

    fun build() = Education(
        teacherFirstName,
        teacherLastName,
        teacherSecondName,
        title,
        duration,
        type,
        links,
        )
}

fun education(block: EducationContext.() -> Unit) = EducationContext().apply(block).build()