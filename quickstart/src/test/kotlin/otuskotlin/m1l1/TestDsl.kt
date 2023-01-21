package otuskotlin.m1l1

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class TestDsl {

    @Test
    fun `dsl test`() {
        val education = education {
            teacher {
                firstName = "teacherFirstName"
                lastName = "teacherLastName"
            }
            lesson {
                title = "dsl education"
                duration = " ~90min"
            }
            type {
                type = LessonType.SELF
            }
            links {
                +"https://www.baeldung.com/kotlin/dsl"
                +"https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-dsl-marker/"
            }
        }

        assertTrue(education.links.contains("https://www.baeldung.com/kotlin/dsl"))
        assertNull(education.teacherSecondName)
        assertEquals(LessonType.SELF, education.type)
    }

}