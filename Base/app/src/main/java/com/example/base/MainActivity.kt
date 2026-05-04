package com.example.base

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.base.databinding.ActivityMainBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: AppDatabase
    private lateinit var adapter: MainAdapter
    private var collectJob: Job? = null

    private var artistsList: List<Artist> = emptyList()
    private var songsList: List<Song> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Инициализируем музыкальную базу данных
        db = AppDatabase.getDatabase(this)
        
        setupUI()
        loadInitialData()
    }

    private fun setupUI() {
        adapter = MainAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.radioGroup.setOnCheckedChangeListener { _, _ ->
            updateSpinnerData()
        }

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateRecyclerView()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun loadInitialData() {
        lifecycleScope.launch {
            val existingArtists = db.appDao().getAllArtists().first()
            if (existingArtists.isEmpty()) {
                populateDatabase()
            }

            launch {
                db.appDao().getAllArtists().collect {
                    artistsList = it
                    if (binding.radioArtist.isChecked) updateSpinnerData()
                }
            }
            launch {
                db.appDao().getAllSongs().collect {
                    songsList = it
                    if (binding.radioSong.isChecked) updateSpinnerData()
                }
            }
        }
    }

    private suspend fun populateDatabase() {
        val a1 = Artist(1, "Linkin Park", "Alternative Rock")
        val a2 = Artist(2, "Eminem", "Hip Hop")
        val a3 = Artist(3, "Hans Zimmer", "Soundtrack")

        val s1 = Song(1, "In the End", "3:36")
        val s2 = Song(2, "Numb", "3:07")
        val s3 = Song(3, "Lose Yourself", "5:26")
        val s4 = Song(4, "Time", "4:35")

        db.appDao().insertArtist(a1)
        db.appDao().insertArtist(a2)
        db.appDao().insertArtist(a3)

        db.appDao().insertSong(s1)
        db.appDao().insertSong(s2)
        db.appDao().insertSong(s3)
        db.appDao().insertSong(s4)

        // Связываем артистов и песни
        db.appDao().insertArtistSong(ArtistSong(1, 1))
        db.appDao().insertArtistSong(ArtistSong(1, 2))
        db.appDao().insertArtistSong(ArtistSong(2, 3))
        db.appDao().insertArtistSong(ArtistSong(3, 4))
    }

    private fun updateSpinnerData() {
        val names = if (binding.radioArtist.isChecked) {
            binding.tvLabel.text = "Песни артиста:"
            artistsList.map { it.name + " (" + it.genre + ")" }
        } else {
            binding.tvLabel.text = "Исполнители песни:"
            songsList.map { it.title }
        }

        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, names)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = spinnerAdapter
        
        if (names.isNotEmpty()) {
            binding.spinner.setSelection(0)
            updateRecyclerView()
        } else {
            adapter.submitList(emptyList())
        }
    }

    private fun updateRecyclerView() {
        val selectedPosition = binding.spinner.selectedItemPosition
        if (selectedPosition < 0) {
            adapter.submitList(emptyList())
            return
        }

        collectJob?.cancel()
        collectJob = lifecycleScope.launch {
            if (binding.radioArtist.isChecked) {
                if (selectedPosition < artistsList.size) {
                    val artistId = artistsList[selectedPosition].artistId
                    db.appDao().getSongsForArtist(artistId).collect { songs ->
                        adapter.submitList(songs.map { it.title + " [" + it.duration + "]" })
                    }
                }
            } else {
                if (selectedPosition < songsList.size) {
                    val songId = songsList[selectedPosition].songId
                    db.appDao().getArtistsForSong(songId).collect { artists ->
                        adapter.submitList(artists.map { it.name + " - " + it.genre })
                    }
                }
            }
        }
    }
}
