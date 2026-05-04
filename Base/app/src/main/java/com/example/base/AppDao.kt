package com.example.base

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Query("SELECT * FROM artists")
    fun getAllArtists(): Flow<List<Artist>>

    @Query("SELECT * FROM songs")
    fun getAllSongs(): Flow<List<Song>>

    @Query("""
        SELECT songs.* FROM songs
        INNER JOIN artist_song ON songs.songId = artist_song.songId
        WHERE artist_song.artistId = :artistId
    """)
    fun getSongsForArtist(artistId: Long): Flow<List<Song>>

    @Query("""
        SELECT artists.* FROM artists
        INNER JOIN artist_song ON artists.artistId = artist_song.artistId
        WHERE artist_song.songId = :songId
    """)
    fun getArtistsForSong(songId: Long): Flow<List<Artist>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtist(artist: Artist)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSong(song: Song)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtistSong(artistSong: ArtistSong)
}
