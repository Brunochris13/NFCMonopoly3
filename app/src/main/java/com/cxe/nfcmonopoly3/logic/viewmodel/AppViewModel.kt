package com.cxe.nfcmonopoly3.logic.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.cxe.nfcmonopoly3.data.database.GameDatabase
import com.cxe.nfcmonopoly3.data.repository.GameRepository

const val STARTING_MONEY = 1500
const val STARTING_MONEY_MEGA = 2500
const val GO_AMOUNT = 200
const val PRISON_AMOUNT = 50
const val LUXURY_TAX_AMOUNT = 100
const val INCOME_TAX_AMOUNT = 200

class AppViewModel(application: Application) : AndroidViewModel(application) {

    // Repository
    private val repository: GameRepository

    init {
        // Database (Dao & Repository)
        val gameDao = GameDatabase.getDatabase(application).gameDao()
        repository = GameRepository(gameDao)
    }
}