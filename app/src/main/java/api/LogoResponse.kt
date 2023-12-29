package api



data class ResponseItem(
    val name: String,
    val ticker: String,
    val image: String
)


data class Response(
    val response: List<ResponseItem>
)