package com.cxe.nfcmonopoly3.data.entities.partial_entities.game

import java.util.*

data class GameStartedPartialEntity(
    val gameId: Long,
    val gameStarted: Boolean = true,
    val updatedOn: Date = Calendar.getInstance().time
)