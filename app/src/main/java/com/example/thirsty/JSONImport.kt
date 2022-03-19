import android.content.Context

fun getJsonDataFromFile(context: Context, fileName: String): String? {
    val jsonStr: String
    try {
        jsonStr = context.assets.open(fileName).bufferedReader().use {
            it.readText()
        }
    } catch (exception : Exception) {
        print(exception.message)
        return null
    }
    return jsonStr
}