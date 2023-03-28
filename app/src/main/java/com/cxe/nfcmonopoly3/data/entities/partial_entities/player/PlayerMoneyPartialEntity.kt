package com.cxe.nfcmonopoly3.data.entities.partial_entities.player

import java.util.*

data class PlayerMoneyPartialEntity(
    val playerId: Int,
    val money: Int,
    val updatedOn: Date = Calendar.getInstance().time
)