package com.srhdp.comicslibrary.model.db

import androidx.room.*
import com.srhdp.comicslibrary.model.db.Constants.CHARACTER_TABLE
import com.srhdp.comicslibrary.model.db.Constants.ID
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Query("SELECT * FROM $CHARACTER_TABLE ORDER BY $ID ASC")
    fun getCharacters(): Flow<List<DbCharacter>>

    @Query("SELECT * FROM $CHARACTER_TABLE WHERE $ID = :characterId")
    fun getCharacter(characterId: Int): Flow<DbCharacter>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addCharacter(character: DbCharacter)

    @Update
    fun updateCharacter(character: DbCharacter)

    @Delete
    fun deleteCharacter(character: DbCharacter)

}