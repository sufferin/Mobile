package com.example.base

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(tableName = "artists")
data class Artist(
    @PrimaryKey val artistId: Long,
    val name: String,
    val genre: String
)

@Entity(tableName = "songs")
data class Song(
    @PrimaryKey val songId: Long,
    val title: String,
    val duration: String
)

@Entity(
    tableName = "artist_song",
    primaryKeys = ["artistId", "songId"],
    foreignKeys = [
        ForeignKey(
            entity = Artist::class,
            parentColumns = ["artistId"],
            childColumns = ["artistId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Song::class,
            parentColumns = ["songId"],
            childColumns = ["songId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("artistId"), Index("songId")]
)
data class ArtistSong(
    val artistId: Long,
    val songId: Long
)
