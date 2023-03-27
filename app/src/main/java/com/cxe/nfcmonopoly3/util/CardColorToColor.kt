package com.cxe.nfcmonopoly3.util

import android.content.Context
import com.cxe.nfcmonopoly3.logic.enums.CardColor
import com.example.nfcmonopoly3.R

object CardColorToColor {
    @JvmStatic
    fun convert(context: Context, cardColor: CardColor): Int {
        return when(cardColor) {
            CardColor.RED_CARD -> context.getColor(R.color.player_red)
            CardColor.BLUE_CARD -> context.getColor(R.color.player_blue)
            CardColor.YELLOW_CARD -> context.getColor(R.color.player_yellow)
            CardColor.GREEN_CARD -> context.getColor(R.color.player_green)
            CardColor.PINK_CARD -> context.getColor(R.color.player_pink)
            CardColor.ORANGE_CARD -> context.getColor(R.color.player_orange)
            CardColor.BROWN_CARD -> context.getColor(R.color.player_brown)
            CardColor.PURPLE_CARD -> context.getColor(R.color.player_purple)
        }
    }
}