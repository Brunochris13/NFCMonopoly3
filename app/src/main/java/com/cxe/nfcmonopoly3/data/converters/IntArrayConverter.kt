package com.cxe.nfcmonopoly3.data.converters

import androidx.room.TypeConverter

class IntArrayConverter {

    @TypeConverter
    fun fromString(stringListString: String): IntArray {
        return stringListString.removeSurrounding("[", "]").replace(" ", "").split(",")
            .map { it.toInt() }.toIntArray()
    }

    @TypeConverter
    fun toString(intArray: IntArray): String {
        return intArray.joinToString(separator = ",")
    }
}