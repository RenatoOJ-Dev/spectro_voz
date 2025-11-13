package br.edu.spectrovoz

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.firebase.messaging.FirebaseMessaging // ← Para notificações
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var btnRecord: Button
    private lateinit var mediaRecorder: MediaRecorder
    private val REQUEST_RECORD_AUDIO_PERMISSION = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 👇 Obter token FCM (necessário para notificações reais)
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                // Mostra os primeiros 20 caracteres no Toast (token completo no Logcat)
                Toast.makeText(this, "Token: ${token.take(20)}...", Toast.LENGTH_LONG).show()
                println("FCM Token: $token") // ← Copie este valor no Logcat
            } else {
                Toast.makeText(this, "Erro ao obter token", Toast.LENGTH_SHORT).show()
                println("Erro FCM: ${task.exception}")
            }
        }

        // Vincular botões
        btnRecord = findViewById(R.id.btnRecord)
        val btnViewHistoryFromMain = findViewById<MaterialButton>(R.id.btnViewHistoryFromMain)

        // Configurar clique do botão de histórico
        btnViewHistoryFromMain.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }

        // Verificar permissão de áudio
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                REQUEST_RECORD_AUDIO_PERMISSION
            )
        } else {
            btnRecord.isEnabled = true
            setupClick()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                btnRecord.isEnabled = true
                setupClick()
            } else {
                Toast.makeText(this, "Permissão necessária para usar o app.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupClick() {
        btnRecord.setOnClickListener {
            startRecording()
        }
    }

    private fun startRecording() {
        val outputFile = "${externalCacheDir?.absolutePath}/temp.3gp"

        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(outputFile)
        }

        try {
            mediaRecorder.prepare()
            mediaRecorder.start()

            // Gravar por 5 segundos e ir para o resultado
            Handler(Looper.getMainLooper()).postDelayed({
                mediaRecorder.stop()
                mediaRecorder.release()

                startActivity(Intent(this, ResultActivity::class.java))
            }, 5000)

        } catch (e: IOException) {
            Toast.makeText(this, "Erro na gravação", Toast.LENGTH_SHORT).show()
        }
    }
}