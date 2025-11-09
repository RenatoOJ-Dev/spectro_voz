package br.edu.spectrovoz

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)


        val barChart = findViewById<BarChart>(R.id.barChart)
        barChart.setBackgroundColor(Color.WHITE)

        // Dados simulados: 0 = calmo, 1 = ansioso, 2 = crise
        val simulatedData = listOf(0f, 0f, 1f, 0f, 2f, 1f, 0f) // últimos 7 dias

        val entries = simulatedData.mapIndexed { index, value ->
            BarEntry(index.toFloat(), value)
        }

        val dataSet = BarDataSet(entries, "Estado Emocional")
        dataSet.colors = listOf(
            Color.parseColor("#C8E6C9"), // calmo (verde suave)
            Color.parseColor("#FFF9C4"), // ansioso (amarelo suave)
            Color.parseColor("#FFECB3")  // crise (laranja suave)
        )
        dataSet.valueTextSize = 12f

        val barData = BarData(dataSet)
        barChart.data = barData

        // Rótulos dos dias
        val days = listOf("Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb")
        barChart.xAxis.valueFormatter = IndexAxisValueFormatter(days)
        barChart.xAxis.granularity = 1f

        barChart.description.isEnabled = false
        barChart.legend.isEnabled = false
        barChart.animateY(1000)

        barChart.invalidate()
    }
}