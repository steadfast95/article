import kotlinx.datetime.Instant
import java.time.Clock

private val INSTANT_NONE = Instant.fromEpochMilliseconds(Long.MIN_VALUE)
val Instant.Companion.NONE
    get() = INSTANT_NONE

val Instant.Companion.NOW
    get() = Clock.systemUTC().instant()
