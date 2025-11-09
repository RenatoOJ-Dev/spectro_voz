package br.edu.spectrovoz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class OnboardingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

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
