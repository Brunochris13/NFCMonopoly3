package com.cxe.nfcmonopoly3.data.entities.partial_entities.game

import java.util.*

data class GameMegaPartialEntity(
    val gameId: Long,
    val mega: Boolean,
    val updatedOn: Date = Calendar.getInstance().time
)