package com.cxe.nfcmonopoly3.data.dao

import androidx.room.*
import com.cxe.nfcmonopoly3.data.entities.GameEntity
import com.cxe.nfcmonopoly3.data.entities.PlayerEntity
import com.cxe.nfcmonopoly3.data.entities.PropertyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {

    @Query("SELECT gameId FROM games WHERE isActive = 1")
    suspend fun getActiveGameId(): Int?

    // General
    /* ----------------------------------------- */

    // Insert

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGameEntity(gameEntity: GameEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayerEntity(playerEntity: PlayerEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPropertyEntity(propertyEntity: PropertyEntity)

    // Delete

    @Delete
    suspend fun deleteGameEntity(gameEntity: GameEntity)

    @Delete
    suspend fun deletePlayerEntity(playerEntity: PlayerEntity)

    @Delete
    suspend fun deletePropertyEntity(propertyEntity: PropertyEntity)

    // Query

    @Query("SELECT * from games WHERE gameId = :gameId")
    suspend fun getGameEntity(gameId: Int): Flow<GameEntity>

    @Query("SELECT * from players WHERE gameId = :gameId ORDER BY sortOrder ASC")
    suspend fun getPlayerEntities(gameId: Int): Flow<List<PlayerEntity>>

    @Query("SELECT * from properties WHERE gameId = :gameId")
    suspend fun getPropertyEntities(gameId: Int): Flow<List<PropertyEntity>>

}