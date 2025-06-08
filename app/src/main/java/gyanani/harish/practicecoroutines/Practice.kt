package gyanani.harish.practicecoroutines

import android.widget.TextView
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import gyanani.harish.practicecoroutines.Utils.appendAndLog
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import java.util.concurrent.Executors

object Practice {
    fun parallelCalls(lifecycleScope: LifecycleCoroutineScope, text1: TextView) {
        lifecycleScope.launch {
            val startTime = Date().time
            val results: MutableList<String> = mutableListOf()
            results.add("Current Thread in lifecycleScope is ${Thread.currentThread().name} time=${Date()}")
            withContext(Dispatchers.IO) {
                results.add("Current Thread in withContext(io) is ${Thread.currentThread().name}")
                val users = async { Utils.getUsers() }
                val posts = async { Utils.getPosts() }
                val todos = async { Utils.getTodos() }
                results.addAll(awaitAll(users, posts, todos))
            }
            results.add("Current Thread after withContext(io) is ${Thread.currentThread().name}\n Totaltime=${(Date().time - startTime)}")
            results.forEach {
                text1.appendAndLog(it)
                text1.appendAndLog("\n\n")
            }
        }
    }

    fun serialCalls(lifecycleScope: LifecycleCoroutineScope, text1: TextView) {
        lifecycleScope.launch {
            val results: MutableList<String> = mutableListOf()
            val startTime = Date().time
            results.add("Current Thread in lifecycleScope is ${Thread.currentThread().name}  time=${Date()}")
            withContext(Dispatchers.IO) {
                results.add("Current Thread in withContext(io) is ${Thread.currentThread().name}")
                withContext(Dispatchers.Main) {
                    results.forEach {
                        text1.appendAndLog(it)
                        text1.appendAndLog("\n\n")
                    }
                }
                val users = Utils.getUsers()
                withContext(Dispatchers.Main) {
                    text1.appendAndLog(users)
                    text1.appendAndLog("\n\n")
                }
                val posts = Utils.getPosts()
                withContext(Dispatchers.Main) {
                    text1.appendAndLog(posts)
                    text1.appendAndLog("\n\n")
                }
                val todos = Utils.getTodos()
                withContext(Dispatchers.Main) {
                    text1.appendAndLog(todos)
                    text1.appendAndLog("\n\n")
                }
            }
            text1.appendAndLog("Current Thread after withContext(io) is ${Thread.currentThread().name}" +
                    "\n Totaltime=${(Date().time-startTime)}")
            text1.appendAndLog("\n\n")

        }
    }

    fun parallelCallsButMaxConcurrent(lifecycleScope: LifecycleCoroutineScope, customDispatcher: CoroutineDispatcher, text1: TextView) {

        lifecycleScope.launch {
            val startTime = Date().time
            val results: MutableList<String> = mutableListOf()
            results.add("Current Thread in lifecycleScope is ${Thread.currentThread().name} time=${Date()}")
            withContext(Dispatchers.IO) {
                results.add("Current Thread in withContext(io) is ${Thread.currentThread().name}")
                val users = async(customDispatcher) { Utils.getUsers() }
                val posts = async(customDispatcher) { Utils.getPosts() }
                val todos = async(customDispatcher) { Utils.getTodos() }
                results.addAll(awaitAll(users, posts, todos))
            }
            results.add("Current Thread after withContext(io) is ${Thread.currentThread().name}\n Totaltime=${(Date().time - startTime)}")
            results.forEach {
                text1.appendAndLog(it)
                text1.appendAndLog("\n\n")
            }
        }
    }
}