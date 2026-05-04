package com.example.room

import androidx.room.*

@Dao
interface CompanyDao {
    @Query("SELECT * FROM companies ORDER BY capitalization DESC")
    suspend fun getAll(): List<Company>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(companies: List<Company>)

    @Query("DELETE FROM companies WHERE name LIKE '%' || :substring || '%'")
    suspend fun deleteBySubstring(substring: String)

    @Query("SELECT SUM(capitalization) FROM companies")
    suspend fun getTotalCapitalization(): Long?

    @Query("SELECT COUNT(*) FROM companies WHERE capitalization > (SELECT AVG(capitalization) FROM companies)")
    suspend fun getCountAboveAverage(): Int

    @Query("SELECT COUNT(*) FROM companies WHERE name < 'A'")
    suspend fun getCountEnglishNames(): Int

    @Query("SELECT name FROM companies ORDER BY capitalization DESC, name ASC LIMIT 1")
    suspend fun getHighestCapitalizationName(): String?

    @Query("SELECT name FROM companies ORDER BY LENGTH(name) DESC, name ASC LIMIT 1")
    suspend fun getLongestName(): String?

    @Query("SELECT COUNT(*) FROM companies")
    suspend fun getCount(): Int
}
