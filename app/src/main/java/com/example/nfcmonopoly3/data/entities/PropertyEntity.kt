package com.example.nfcmonopoly3.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nfcmonopoly3.logic.enums.*
import java.util.*

@Entity(tableName = "properties")
data class PropertyEntity(
    @PrimaryKey(autoGenerate = true)
    val propertyId: Long,
    val gameId: Long,
    val propertyCard: PropertyCard,
    val name: String,
    val price: Int,
    val rent: IntArray,
    val mortgaged: Boolean,
    val playerId: CardColor?,
    val currentRentLevel: Int,
    // Mega Edition
    val mega: Boolean,
    // Property Type (ColorProperty, StationProperty, UtilityProperty)
    val propertyType: PropertyType? = null,
    // Color Property
    val color: PropertyColor? = null,
    val housePrice: Int? = null,
    val colorSetPropertyAmount: Int? = null,
    val set: Boolean? = null,
    val megaSet: Boolean? = null,
    // Station Property
    val depot: Boolean? = null,
    // Utility Property
    val utilityType: UtilityType? = null,
    // Other
    val createdOn: Date = Calendar.getInstance().time,
    val updatedOn: Date = Calendar.getInstance().time,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PropertyEntity

        if (!rent.contentEquals(other.rent)) return false

        return true
    }

    override fun hashCode(): Int {
        return rent.contentHashCode()
    }
}