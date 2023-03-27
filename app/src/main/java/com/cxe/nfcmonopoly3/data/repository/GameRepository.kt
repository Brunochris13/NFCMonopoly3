package com.cxe.nfcmonopoly3.data.repository

import com.cxe.nfcmonopoly3.data.dao.GameDao
import com.cxe.nfcmonopoly3.data.entities.GameEntity
import com.cxe.nfcmonopoly3.data.entities.PlayerEntity

private const val LOG_TAG = "GameRepository"

class GameRepository(private val gameDao: GameDao) {

    suspend fun createGame(): Long {
        // Insert Game Entity
        // Get gameId that was just created
        return gameDao.insertGameEntity(GameEntity())
    }

    suspend fun insertPlayer(playerEntity: PlayerEntity): Long {
        // Insert player
        return gameDao.insertPlayerEntity(playerEntity)
    }

}