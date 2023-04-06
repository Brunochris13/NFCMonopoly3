package com.cxe.nfcmonopoly3.data.dao

import androidx.room.*
import com.cxe.nfcmonopoly3.data.entities.Game
import com.cxe.nfcmonopoly3.data.entities.Player
import com.cxe.nfcmonopoly3.data.entities.Property
import com.cxe.nfcmonopoly3.data.entities.partial_entities.game.GameMegaPartialEntity
import com.cxe.nfcmonopoly3.data.entities.partial_entities.player.PlayerMoneyPartialEntity
import com.cxe.nfcmonopoly3.data.entities.partial_entities.player.PlayerNamePartialEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {

    // Game
    @Query("SELECT gameId FROM games WHERE isActive = 1")
    suspend fun getActiveGameId(): Long?

    @Query("SELECT gameStarted FROM GAMES WHERE gameId = :gameId")
    suspend fun hasGameStarted(gameId: Long): Boolean

    @Update(entity = Game::class)
    suspend fun setMegaEditionStatus(gameMegaPartialEntity: GameMegaPartialEntity)

    @Update(entity = Game::class)
    suspend fun startGame(gameMegaPartialEntity: GameMegaPartialEntity)

    // Player
    @Update(entity = Player::class)
    suspend fun setPlayerMoney(playerMoneyPartialEntity: PlayerMoneyPartialEntity)

    @Update(entity = Player::class)
    suspend fun setPlayersStartingMoney(playerMoneyPartialEntities: List<PlayerMoneyPartialEntity>)

    @Update(entity = Player::class)
    suspend fun changePlayerName(playerNamePartialEntity: PlayerNamePartialEntity)

    // General
    /* ----------------------------------------- */

    // Insert

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: Game): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(player: Player): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProperty(property: Property)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProperties(properties: List<Property>)

    // Delete

    @Delete
    suspend fun deleteGame(game: Game)

    @Delete
    suspend fun deletePlayer(player: Player)

    @Delete
    suspend fun deleteProperty(property: Property)

    // Query

    @Query("SELECT * from games WHERE gameId = :gameId")
    fun getGame(gameId: Long): Flow<Game>

    @Query("SELECT * from players WHERE gameId = :gameId ORDER BY playerId ASC")
    fun getPlayers(gameId: Long): Flow<List<Player>>

    @Query("SELECT * from properties WHERE gameId = :gameId")
    fun getProperties(gameId: Long): Flow<List<Property>>

}