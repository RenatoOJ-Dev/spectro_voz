package br.edu.spectrovoz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.messaging.FirebaseMessaging // ← novo import

class OnboardingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        // 👇 Obtém e salva o token FCM do dispositivo (como cuidador)
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                prefs.edit().putString("caregiver_fcm_token", token).apply()
                // Opcional: Toast de confirmação (pode remover depois)
                // Toast.makeText(this, "Token do cuidador salvo", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Erro ao salvar token", Toast.LENGTH_SHORT).show()
            }
        }

        val btnStart = findViewById<MaterialButton>(R.id.btnStart)
        val btnSkip = findViewById<TextView>(R.id.btnSkip)

        btnStart.setOnClickListener {
            prefs.edit().putBoolean("first_run", false).apply()
            startMainActivity()
        }

        btnSkip.setOnClickListener {
            prefs.edit().putBoolean("first_run", false).apply()
            startMainActivity()
        }
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}