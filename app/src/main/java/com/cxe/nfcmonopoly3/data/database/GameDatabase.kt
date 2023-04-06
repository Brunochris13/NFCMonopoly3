package com.cxe.nfcmonopoly3.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cxe.nfcmonopoly3.data.converters.DateConverter
import com.cxe.nfcmonopoly3.data.converters.IntArrayConverter
import com.cxe.nfcmonopoly3.data.dao.GameDao
import com.cxe.nfcmonopoly3.data.entities.Game
import com.cxe.nfcmonopoly3.data.entities.Player
import com.cxe.nfcmonopoly3.data.entities.Property

@Database(
    entities = [Game::class, Player::class, Property::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class, IntArrayConverter::class)
abstract class GameDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDao

    companion object {
        @Volatile
        private var INSTANCE: GameDatabase? = null
        fun getDatabase(context: Context): GameDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameDatabase::class.java,
                    "game_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
