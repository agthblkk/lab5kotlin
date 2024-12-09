package com.example.lab5kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val breakerTypeInput: EditText = findViewById(R.id.inputBreakerType)
        val lineLengthInput: EditText = findViewById(R.id.inputLineLength)
        val transformerTypeInput: EditText = findViewById(R.id.inputTransformerType)
        val incomingBreakerInput: EditText = findViewById(R.id.inputIncomingBreaker)
        val connectionsInput: EditText = findViewById(R.id.inputConnections)
        val singleCircuitCheckbox: CheckBox = findViewById(R.id.checkboxSingleCircuit)
        val calculateButton: Button = findViewById(R.id.btnCalculate)
        val resultTextView: TextView = findViewById(R.id.textResult)
        val toLossCalculatorButton: Button = findViewById(R.id.btnToLossCalculator)
        toLossCalculatorButton.setOnClickListener {
            val intent = Intent(this, LossCalculatorActivity::class.java)
            startActivity(intent)
        }
        calculateButton.setOnClickListener {
            val breakerType = breakerTypeInput.text.toString()
            val lineLength = lineLengthInput.text.toString().toDoubleOrNull()
            val transformerType = transformerTypeInput.text.toString()
            val incomingBreaker = incomingBreakerInput.text.toString()
            val connections = connectionsInput.text.toString().toIntOrNull()
            val isSingleCircuit = singleCircuitCheckbox.isChecked

            if (breakerType.isEmpty() || lineLength == null || transformerType.isEmpty() ||
                incomingBreaker.isEmpty() || connections == null) {
                Toast.makeText(this, "Будь ласка, заповніть усі поля!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val system = PowerSystem(breakerType, lineLength, transformerType, incomingBreaker, connections, isSingleCircuit)
            val reliability = calculateReliability(system)

            resultTextView.text = "Надійність системи: $reliability"
        }
    }

    data class PowerSystem(
        val breakerType: String,
        val lineLength: Double,
        val transformerType: String,
        val incomingBreaker: String,
        val connections: Int,
        val isSingleCircuit: Boolean
    )

    private fun calculateReliability(system: PowerSystem): Double {
        return if (system.isSingleCircuit) {
            // Формула для одноколової системи
            val omega_oc = 0.295 // Частота відмов
            val t_v_oc = 10.7 // Тривалість простою
            1 - (omega_oc * t_v_oc / 8760) // Надійність
        } else {
            // Формула для двоколової системи
            val omega_dc = 0.0237 // Частота відмов
            val t_v_dc = 10.7 // Тривалість простою
            1 - (omega_dc * t_v_dc / 8760) // Надійність
        }
    }
}