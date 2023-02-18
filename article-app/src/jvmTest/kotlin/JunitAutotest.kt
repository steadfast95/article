import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class JunitAutotest {
    @ParameterizedTest
    @MethodSource("prepare")
    fun jvmAutoParametrizedTest(first: String, second: String, expected: String) {
        Assertions.assertEquals(expected, first + second)
    }

    companion object {
        @JvmStatic
        fun prepare(): List<Arguments> = listOf(
            Arguments.of("Hello", " world", "Hello world"),
            Arguments.of("Hello", " autotest", "Hello autotest")
        )
    }
}