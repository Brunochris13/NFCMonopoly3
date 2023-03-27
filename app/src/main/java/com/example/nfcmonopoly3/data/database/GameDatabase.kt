package com.example.nfcmonopoly3.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.nfcmonopoly3.data.converters.DateConverter
import com.example.nfcmonopoly3.data.converters.IntArrayConverter
import com.example.nfcmonopoly3.data.dao.GameDao
import com.example.nfcmonopoly3.data.entities.GameEntity
import com.example.nfcmonopoly3.data.entities.PlayerEntity
import com.example.nfcmonopoly3.data.entities.PropertyEntity

@Database(
    entities = [GameEntity::class, PlayerEntity::class, PropertyEntity::class],
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
