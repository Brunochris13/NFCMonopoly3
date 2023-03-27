package com.cxe.nfcmonopoly3.data.entities.partial_entities.game

import java.util.*

data class FreeParkingMoneyPartialEntity(
    val gameId: Long,
    val freeParkingMoney: Int,
    val updatedOn: Date = Calendar.getInstance().time
)