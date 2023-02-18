import io.kotest.core.spec.style.ShouldSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe


class Autotest : ShouldSpec({
    withData(
        mapOf(
            "world" to Triple("Hello", " world", "Hello world"),
            "autotest" to Triple("Hello", " autotest", "Hello autotest")
        )
    ) { (f, s, r) -> r shouldBe f + s }
})