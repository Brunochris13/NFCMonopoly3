package com.cxe.nfcmonopoly3.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "games")
data class Game(
    @PrimaryKey
    @ColumnInfo(name = "gameId")
    val gameId: Long? = null,
    @ColumnInfo(name = "freeParkingMoney")
    val freeParkingMoney: Int = 0,
    @ColumnInfo(name = "mega")
    val mega: Boolean = false,
    @ColumnInfo(name = "isActive")
    val isActive: Boolean = true,
    @ColumnInfo(name = "gameStarted")
    val gameStarted: Boolean = false,
    @ColumnInfo(name = "createdOn")
    val createdOn: Date = Calendar.getInstance().time,
    @ColumnInfo(name = "updatedOn")
    val updatedOn: Date = Calendar.getInstance().time
)