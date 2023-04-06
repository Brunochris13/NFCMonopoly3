package com.cxe.nfcmonopoly3.logic.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.cxe.nfcmonopoly3.data.dao.GameDao
import com.cxe.nfcmonopoly3.data.database.GameDatabase
import com.cxe.nfcmonopoly3.data.entities.Game
import com.cxe.nfcmonopoly3.data.entities.Player
import com.cxe.nfcmonopoly3.data.entities.partial_entities.game.GameMegaPartialEntity
import com.cxe.nfcmonopoly3.data.entities.partial_entities.player.PlayerMoneyPartialEntity
import com.cxe.nfcmonopoly3.data.entities.partial_entities.player.PlayerNamePartialEntity
import com.cxe.nfcmonopoly3.logic.enums.CardColor
import com.cxe.nfcmonopoly3.util.STARTING_MONEY
import com.cxe.nfcmonopoly3.util.STARTING_MONEY_MEGA
import com.cxe.nfcmonopoly3.util.createProperties
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val LOG_TAG = "AppViewModel"

abstract class AppViewModel(application: Application) : AndroidViewModel(application) {

    // Dao
    private val gameDao: GameDao

    // Main Logic
    private var currentGameId: Long? = null

    private var _hasGameStarted = MutableLiveData<Boolean>()
    val hasGameStarted: LiveData<Boolean> = _hasGameStarted

    var players: LiveData<List<Player>>? = null

    init {
        // Database (Dao & Repository)
        gameDao = GameDatabase.getDatabase(application).gameDao()

        // Start coroutine
        viewModelScope.launch {
            currentGameId = gameDao.getActiveGameId()

            if (currentGameId != null) {
                // Check if the existing game has already started
                if (gameDao.hasGameStarted(currentGameId!!))
                // Main Thread
                    withContext(Dispatchers.Main) {
                        _hasGameStarted.value = true
                    }
            } else
            // Create New Game
                currentGameId = gameDao.insertGame(Game())

            // Get Players
            players = gameDao.getPlayers(currentGameId!!).asLiveData()
        }
    }

    // Game
    fun setMegaStatus(mega: Boolean) {
        if (currentGameId == null) {
            Log.e(LOG_TAG, "Tried to set Mega Edition status, but currentGameId was null")
            return
        }

        viewModelScope.launch {
            gameDao.setMegaEditionStatus(
                GameMegaPartialEntity(
                    gameId = currentGameId!!,
                    mega = mega
                )
            )
        }
    }

    // Player
    fun playerExists(cardColor: CardColor) = players?.value!!.any { it.cardColor == cardColor }

    fun addPlayer(cardColor: CardColor, name: String) {
        if (currentGameId == null) {
            Log.e(LOG_TAG, "Tried to add new Player, but currentGameId was null")
            return
        }

        val newPlayer = Player(
            gameId = currentGameId!!,
            cardColor = cardColor,
            name = name
        )

        viewModelScope.launch {
            gameDao.insertPlayer(newPlayer)
        }
    }

    fun changePlayerName(cardColor: CardColor, newName: String) {
        viewModelScope.launch {
            gameDao.changePlayerName(
                PlayerNamePartialEntity(
                    playerId = getPlayer(cardColor).playerId!!,
                    name = newName
                )
            )
        }
    }

    fun getPlayer(cardColor: CardColor) = players?.value!!.first { it.cardColor == cardColor }

    fun deletePlayer(player: Player) {
        viewModelScope.launch { gameDao.deletePlayer(player) }
    }

    fun setPlayersStartingMoney(mega: Boolean) {
        viewModelScope.launch {
            val playerStartingMoneyPartialEntities = mutableListOf<PlayerMoneyPartialEntity>()

            for (player in players?.value ?: emptyList())
                playerStartingMoneyPartialEntities.add(
                    PlayerMoneyPartialEntity(
                        playerId = player.playerId!!,
                        money = if (mega) STARTING_MONEY_MEGA else STARTING_MONEY
                    )
                )

            if (playerStartingMoneyPartialEntities.isNotEmpty())
                gameDao.setPlayersStartingMoney(playerStartingMoneyPartialEntities)
        }
    }

    // Property
    fun createPropertiesFromJson(jsonFileName: String, mega: Boolean) {
        if (currentGameId == null) {
            Log.e(LOG_TAG, "Tried to create Properties, but currentGameId was null")
            return
        }

        viewModelScope.launch {
            // Create Properties from JSON
            val properties = createProperties(
                getApplication(),
                jsonFileName,
                currentGameId!!,
                mega
            )

            // Insert Properties in the Database
            gameDao.insertProperties(properties)
        }
    }
}