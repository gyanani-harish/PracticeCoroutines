package gyanani.harish.practicecoroutines

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import gyanani.harish.practicecoroutines.Utils.appendAndLog
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.Date
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class SecondActivity : AppCompatActivity() {
    private lateinit var threadPool: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val text1 = findViewById<TextView>(R.id.text1)
        text1.appendAndLog("Starting...\n")
        if (intent.getStringExtra("type") == "parallel") {
            Practice.parallelCalls(lifecycleScope, text1)
        } else if (intent.getStringExtra("type") == "serial") {
            Practice.serialCalls(lifecycleScope, text1)
        } else if (intent.getStringExtra("type") == "parallelButMaxConcurrent") {
            threadPool = Executors.newFixedThreadPool(2)
            val customDispatcher = threadPool.asCoroutineDispatcher()
            Practice.parallelCallsButMaxConcurrent(lifecycleScope, customDispatcher, text1)
        }
        text1.appendAndLog("Ending...\n")
    }

    override fun onDestroy() {
        super.onDestroy()
        threadPool.shutdown()
    }
}