package br.edu.spectrovoz

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.functions.FirebaseFunctions
import kotlin.random.Random

class ResultActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        val ivResultIcon = findViewById<ImageView>(R.id.ivResultIcon)
        val tvResultMessage = findViewById<TextView>(R.id.tvResultMessage)
        val btnViewHistory = findViewById<MaterialButton>(R.id.btnViewHistory)

        // Simular resultado (60% calmo, 30% ansioso, 10% crise)
        val states = listOf("CALM", "CALM", "CALM", "CALM", "CALM", "CALM", "ANXIOUS", "ANXIOUS", "ANXIOUS", "CRISIS")
        val result = states[Random.nextInt(states.size)]

        when (result) {
            "CALM" -> {
                ivResultIcon.setImageResource(R.drawable.ic_calm)
                tvResultMessage.text = "Você parece calmo."
            }
            "ANXIOUS" -> {
                ivResultIcon.setImageResource(R.drawable.ic_anxious)
                tvResultMessage.text = "Talvez esteja se sentindo ansioso."
                // ✅ Envia notificação ao cuidador
                sendAlertToCaregiver("Sinais de ansiedade detectados. Ofereça um espaço calmo.")
            }
            "CRISIS" -> {
                ivResultIcon.setImageResource(R.drawable.ic_crisis)
                tvResultMessage.text = "Está tudo bem. Respire fundo."
                // ✅ Envia notificação ao cuidador
                sendAlertToCaregiver("Emergência: sinais de crise detectados. Intervenção imediata recomendada.")
            }
        }

        btnViewHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
    }

    private fun sendAlertToCaregiver(message: String) {
        // 🔑 Obtém o token do cuidador (salvo na Tela de Cadastro)
        val caregiverToken = prefs.getString("caregiver_fcm_token", null)

        if (caregiverToken.isNullOrEmpty()) {
            // 🔁 Para teste: use o token do próprio dispositivo (você mesmo é o cuidador)
            // Adicione temporariamente no MainActivity.kt: prefs.edit().putString("caregiver_fcm_token", token).apply()
            Toast.makeText(this, "⚠️ Token do cuidador não configurado", Toast.LENGTH_LONG).show()
            return
        }

        val functions = FirebaseFunctions.getInstance()
        val data = hashMapOf(
            "fcmToken" to caregiverToken,
            "message" to message
        )

        functions
            .getHttpsCallable("sendStressAlert")
            .call(data)
            .addOnSuccessListener {
                Toast.makeText(this, "✅ Notificação enviada ao cuidador!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "❌ Erro: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }
}