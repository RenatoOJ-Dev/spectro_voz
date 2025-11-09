package br.edu.spectrovoz

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import kotlin.random.Random

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val ivResultIcon = findViewById<ImageView>(R.id.ivResultIcon)
        val tvResultMessage = findViewById<TextView>(R.id.tvResultMessage)
        val btnViewHistory = findViewById<MaterialButton>(R.id.btnViewHistory)

        // Simular resultado (60% calmo, 30% ansioso, 10% crise)
        val states = listOf("CALM", "CALM", "CALM", "CALM", "CALM", "CALM", "ANXIOUS", "ANXIOUS", "ANXIOUS", "CRISIS")
        val result = states[Random.nextInt(states.size)]

        when (result) {
            "CALM" -> {
                ivResultIcon.setImageResource(R.drawable.ic_calm) // 😊
                tvResultMessage.text = "Você parece calmo."
            }
            "ANXIOUS" -> {
                ivResultIcon.setImageResource(R.drawable.ic_anxious) // 😟
                tvResultMessage.text = "Talvez esteja se sentindo ansioso."
            }
            "CRISIS" -> {
                ivResultIcon.setImageResource(R.drawable.ic_crisis) // 😫
                tvResultMessage.text = "Está tudo bem. Respire fundo."
            }
        }

        btnViewHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
    }
}
