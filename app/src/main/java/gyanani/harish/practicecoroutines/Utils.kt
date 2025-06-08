package gyanani.harish.practicecoroutines

import android.util.Log
import android.widget.TextView
import kotlinx.coroutines.delay
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

object Utils {
    suspend fun getUsers(): String {
        return apiCalling("https://jsonplaceholder.typicode.com/users/1")
    }

    suspend fun getPosts(): String {
        return apiCalling("https://jsonplaceholder.typicode.com/posts/1")
    }

    suspend fun getTodos(): String {
        return apiCalling("https://jsonplaceholder.typicode.com/todos/1")
    }


    suspend fun apiCalling(urlStr: String): String {
        Log.d("Append", "apiCalling->start of API calling url=$urlStr")
        delay(2000)
        Log.d("Append", "apiCalling->resumed after 2 seconds of sleep url=$urlStr")
        val url = URL(urlStr)
        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        urlConnection.requestMethod = "GET"
        return try {
            val inputStream: InputStream = urlConnection.inputStream
            readStream(inputStream, urlStr)
        } catch (e: Exception) {
            e.message.toString()
        }
    }

    private fun readStream(inputStream: InputStream, url: String? = null): String {
        Log.d("Append", "readStream->start of method, url=$url")
        var reader: BufferedReader? = null
        val response = StringBuffer()
        try {
            reader = BufferedReader(InputStreamReader(inputStream))
            var line: String? = ""
            while (reader.readLine().also { line = it } != null) {
                response.append(line)
            }
            Log.d("Append", "readStream->all reading done, url=$url")
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return response.toString()
    }

    fun TextView.appendAndLog(text: CharSequence){
        this.append(text)
        Log.d("TextViewAppend",text.toString())
    }
}