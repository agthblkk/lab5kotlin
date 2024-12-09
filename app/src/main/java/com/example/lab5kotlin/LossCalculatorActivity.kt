package com.example.lab5kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LossCalculatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loss_calculation)

        // Отримання посилань на елементи інтерфейсу
        val accidentalLossCostInput: EditText = findViewById(R.id.inputAccidentalLossCost)
        val plannedLossCostInput: EditText = findViewById(R.id.inputPlannedLossCost)
        val calculateButton: Button = findViewById(R.id.btnCalculateLoss)
        val resultTextView: TextView = findViewById(R.id.textResultLoss)
        val backButton: Button = findViewById(R.id.btnBackToMain)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        calculateButton.setOnClickListener {
            // Зчитування введених даних
            val zAccidental = accidentalLossCostInput.text.toString().toDoubleOrNull()
            val zPlanned = plannedLossCostInput.text.toString().toDoubleOrNull()

            // Перевірка коректності введення
            if (zAccidental == null || zPlanned == null) {
                Toast.makeText(this, "Будь ласка, заповніть усі поля!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Постійні параметри
            val omega = 0.01 // Частота відмов (рік⁻¹)
            val tRecovery = 45.0 // Середній час відновлення (год)
            val power = 5120.0 // Навантаження трансформатора (кВт)
            val tPlanned = 6451.0 // Тривалість планового простою (год)
            val kPlanned = 4e-3 // Коефіцієнт планового простою

            // Розрахунок аварійного недовідпуску електроенергії
            val mWAccidental = omega * power * tRecovery

            // Розрахунок планового недовідпуску електроенергії
            val mWPlanned = kPlanned * power * tPlanned

            // Розрахунок загальних збитків
            val totalLoss = (mWAccidental * zAccidental) + (mWPlanned * zPlanned)

            // Виведення результату
            resultTextView.text = "Збитки від перерв електропостачання: %.2f грн".format(totalLoss)
        }


    }
}