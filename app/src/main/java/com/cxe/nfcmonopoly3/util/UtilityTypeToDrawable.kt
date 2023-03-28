package com.cxe.nfcmonopoly3.util

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import com.cxe.nfcmonopoly3.logic.enums.UtilityType
import com.example.nfcmonopoly3.R

object UtilityTypeToDrawable {
    @JvmStatic
    fun convert(context: Context,  utilityType: UtilityType): Drawable? {
        return when(utilityType) {
            UtilityType.ELECTRICITY -> AppCompatResources.getDrawable(context, R.drawable.monopoly_light_bulb)
            UtilityType.WATER -> AppCompatResources.getDrawable(context, R.drawable.monopoly_water_tap)
            UtilityType.GAS -> AppCompatResources.getDrawable(context, R.drawable.monopoly_gas_icon)
        }
    }
}