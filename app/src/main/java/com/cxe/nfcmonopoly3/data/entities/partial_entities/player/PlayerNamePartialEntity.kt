package com.cxe.nfcmonopoly3.data.entities.partial_entities.player

import java.util.*

data class PlayerNamePartialEntity(
    val playerId: Long,
    val name: String,
    val updatedOn: Date = Calendar.getInstance().time
)