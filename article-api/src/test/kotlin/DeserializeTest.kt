import org.junit.Assert
import org.junit.Test
import ru.otuskotlin.public.article.api.v1.models.ArticleCreateRequest
import ru.otuskotlin.public.article.api.v1.models.RequestType

class DeserializeTest {
    @Test
    fun deserializeCreateRequestTest(){
        val request = """
        {
          "requestType": "create",
          "requestId": "9c8033b5-46ec-4e77-8f18-e851fbc50425",
          "article": {
            "title": "test title",
            "visibility": "public",
            "content": "1111111111",
            "comments": []
          }
        }
    """.trimIndent()

        val readValue = articleMapper.readValue(request, ArticleCreateRequest::class.java)
        Assert.assertEquals(RequestType.CREATE.value, readValue.requestType)
    }


}