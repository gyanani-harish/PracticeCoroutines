package gyanani.harish.practicecoroutines

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class FirstActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        val btnParallel = findViewById<Button>(R.id.btnParallel)
        btnParallel.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("type", "parallel")
            startActivity(intent)
        }
        val btnSerial = findViewById<Button>(R.id.btnSerial)
        btnSerial.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("type", "serial")
            startActivity(intent)
        }
        val btnParallelButMaxConcurrent = findViewById<Button>(R.id.btnParallelButMaxConcurrent)
        btnParallelButMaxConcurrent.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("type", "parallelButMaxConcurrent")
            startActivity(intent)
        }
    }
}