package com.cxe.nfcmonopoly3.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cxe.nfcmonopoly3.logic.enums.CardColor
import java.util.*

@Entity(tableName = "players")
data class Player(
    @PrimaryKey
    @ColumnInfo(name = "playerId")
    val playerId: Long? = null,
    @ColumnInfo(name = "gameId")
    val gameId: Long,
    @ColumnInfo(name = "cardColor")
    val cardColor: CardColor,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "money")
    var money: Int = 0,
    @ColumnInfo(name = "createdOn")
    val createdOn: Date = Calendar.getInstance().time,
    @ColumnInfo(name = "updatedOn")
    val updatedOn: Date = Calendar.getInstance().time
)