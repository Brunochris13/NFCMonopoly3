package com.cxe.nfcmonopoly3.util

import android.content.Context
import com.cxe.nfcmonopoly3.logic.enums.PropertyColor
import com.example.nfcmonopoly3.R

object PropertyColorToResourcesConverter {

    @JvmStatic
    fun convert(context: Context, propertyColor: PropertyColor): Int {
        return when(propertyColor) {
            PropertyColor.BROWN -> context.getColor(R.color.property_brown)
            PropertyColor.LIGHT_BLUE -> context.getColor(R.color.property_light_blue)
            PropertyColor.PINK -> context.getColor(R.color.property_pink)
            PropertyColor.ORANGE -> context.getColor(R.color.property_orange)
            PropertyColor.RED -> context.getColor(R.color.property_red)
            PropertyColor.YELLOW -> context.getColor(R.color.property_yellow)
            PropertyColor.GREEN -> context.getColor(R.color.property_green)
            PropertyColor.BLUE -> context.getColor(R.color.property_blue)
        }
    }
}