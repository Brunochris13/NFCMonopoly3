package com.example.nfcmonopoly3.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nfcmonopoly3.logic.enums.CardColor
import java.util.*

@Entity(tableName = "players")
data class PlayerEntity(
    @PrimaryKey(autoGenerate = true)
    val playerId: Long = 0,
    @ColumnInfo(name = "gameId")
    val gameId: Long,
    @ColumnInfo(name = "cardColor")
    val cardColor: CardColor,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "money")
    val money: Int,
    @ColumnInfo(name = "sortOrder")
    val sortOrder: Int,
    @ColumnInfo(name = "createdOn")
    val createdOn: Date = Calendar.getInstance().time,
    @ColumnInfo(name = "updatedOn")
    val updatedOn: Date = Calendar.getInstance().time
)