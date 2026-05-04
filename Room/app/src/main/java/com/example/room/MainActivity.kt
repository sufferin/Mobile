package com.example.room

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.room.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = AppDatabase.getDatabase(this)
        val dao = db.companyDao()

        lifecycleScope.launch {
            if (dao.getCount() == 0) {
                dao.insertAll(sampleCompanies())
            }
            refreshList()
        }

        binding.btnDelete.setOnClickListener {
            val substring = binding.etSubstring.text.toString()
            if (substring.isNotEmpty()) {
                lifecycleScope.launch {
                    dao.deleteBySubstring(substring)
                    refreshList()
                }
            }
        }

        binding.btnAnalysis.setOnClickListener {
            startActivity(Intent(this, AnalysisActivity::class.java))
        }
    }

    private fun refreshList() {
        val dao = AppDatabase.getDatabase(this).companyDao()
        lifecycleScope.launch {
            val companies = dao.getAll()
            val text = companies.joinToString("\n") { "${it.name} ${it.capitalization}" }
            binding.tvCompanyList.text = text
        }
    }

    private fun sampleCompanies() = listOf(
        Company(name = "Газпром", capitalization = 68012),
        Company(name = "НК «Роснефть»", capitalization = 62534),
        Company(name = "НОВАТЭК", capitalization = 51630),
        Company(name = "Норильский никель", capitalization = 50604),
        Company(name = "ЛУКОЙЛ", capitalization = 48601),
        Company(name = "Полюс", capitalization = 27738),
        Company(name = "Яндекс", capitalization = 22122),
        Company(name = "Газпром нефть", capitalization = 20406),
        Company(name = "Сургутнефтегаз", capitalization = 17405),
        Company(name = "НЛМК", capitalization = 16941),
        Company(name = "Татнефть", capitalization = 15176),
        Company(name = "Северсталь", capitalization = 15029),
        Company(name = "Полиметалл", capitalization = 11142),
        Company(name = "En+ Group", capitalization = 5000),
        Company(name = "Etalon Group", capitalization = 3000),
        Company(name = "Mail.Ru Group", capitalization = 7000),
        Company(name = "X5 Retail Group", capitalization = 9000),
        Company(name = "Сбербанк", capitalization = 100000),
        Company(name = "Россети Северный Кавказ (МРСК Северного Кавказа)", capitalization = 1200)
    )
}
