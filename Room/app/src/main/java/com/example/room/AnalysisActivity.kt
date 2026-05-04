package com.example.room

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.room.databinding.ActivityAnalysisBinding
import kotlinx.coroutines.launch

class AnalysisActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnalysisBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnalysisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = AppDatabase.getDatabase(this)
        val dao = db.companyDao()

        lifecycleScope.launch {
            val totalCap = dao.getTotalCapitalization() ?: 0L
            val aboveAvgCount = dao.getCountAboveAverage()
            val englishNamesCount = dao.getCountEnglishNames()
            val highestCapCompany = dao.getHighestCapitalizationName() ?: "N/A"
            val longestNameCompany = dao.getLongestName() ?: "N/A"

            binding.tvTotalCapitalization.text = totalCap.toString()
            binding.tvAboveAverageCount.text = aboveAvgCount.toString()
            binding.tvEnglishNamesCount.text = englishNamesCount.toString()
            binding.tvHighestCapitalizationCompany.text = highestCapCompany
            binding.tvLongestNameCompany.text = longestNameCompany
        }
    }
}
