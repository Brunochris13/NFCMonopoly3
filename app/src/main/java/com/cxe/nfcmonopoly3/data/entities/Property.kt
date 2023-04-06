package com.cxe.nfcmonopoly3.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cxe.nfcmonopoly3.logic.enums.*
import java.util.*

@Entity(tableName = "properties")
data class Property(
    @PrimaryKey
    @ColumnInfo(name = "propertyId")
    val propertyId: Long? = null,
    val gameId: Long,
    val propertyCard: PropertyCard,
    val name: String,
    val price: Int,
    val rent: IntArray,
    val mortgaged: Boolean = false,
    val mortgageValue: Int,
    val unMortgageValue: Int,
    val playerCardColor: CardColor? = null,
    val currentRentLevel: Int = 0,
    // Mega Edition
    val mega: Boolean = false,
    // Property Type (ColorProperty, StationProperty, UtilityProperty)
    val propertyType: PropertyType,
    // Color Property
    val color: PropertyColor? = null,
    val houseBuyPrice: Int? = null,
    val houseSellPrice: Int? = null,
    val colorSetPropertyAmount: Int? = null,
    val set: Boolean? = null,
    val megaSet: Boolean? = null,
    // Station Property
    val depot: Boolean? = null,
    val depotBuyPrice: Int? = null,
    val depotSellPrice: Int? = null,
    // Utility Property
    val utilityType: UtilityType? = null,
    // Other
    val createdOn: Date = Calendar.getInstance().time,
    val updatedOn: Date = Calendar.getInstance().time,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Property

        if (!rent.contentEquals(other.rent)) return false

        return true
    }

    override fun hashCode(): Int {
        return rent.contentHashCode()
    }
}