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
import com.google.android.material.button.MaterialButton // ← IMPORT ADICIONADO
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var btnRecord: Button
    private lateinit var mediaRecorder: MediaRecorder
    private val REQUEST_RECORD_AUDIO_PERMISSION = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnRecord = findViewById(R.id.btnRecord)

        // 👇 Agora com MaterialButton
        val btnViewHistoryFromMain = findViewById<MaterialButton>(R.id.btnViewHistoryFromMain)
        btnViewHistoryFromMain.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }

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
                Toast.makeText(this, "Permissão necessária.", Toast.LENGTH_SHORT).show()
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

            Handler(Looper.getMainLooper()).postDelayed({
                mediaRecorder.stop()
                mediaRecorder.release()

                val intent = Intent(this, ResultActivity::class.java)
                startActivity(intent)
            }, 5000)

        } catch (e: IOException) {
            Toast.makeText(this, "Erro na gravação", Toast.LENGTH_SHORT).show()
        }
    }
}