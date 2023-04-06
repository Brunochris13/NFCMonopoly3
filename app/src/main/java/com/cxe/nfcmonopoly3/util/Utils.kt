package com.cxe.nfcmonopoly3.util

import android.content.Context
import android.util.Log
import com.cxe.nfcmonopoly3.data.entities.Property
import com.cxe.nfcmonopoly3.logic.enums.*
import org.json.JSONObject
import java.io.IOException
import kotlin.math.roundToInt

private const val LOG_TAG = "Utils"

fun getJsonDataFromAsset(context: Context, fileName: String): String? {
    val jsonString: String
    try {
        jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    }
    return jsonString
}

fun createProperties(
    context: Context,
    jsonFileName: String,
    gameId: Long,
    mega: Boolean
): List<Property> {
    val propertyList = mutableListOf<Property>()

    // Read JSON file from Assets
    val jsonFileString = getJsonDataFromAsset(context, jsonFileName)

    // Check if file was read from Assets
    if (jsonFileString != null) {
        val jsonArrayObject = JSONObject(jsonFileString)
        val jsonArray = jsonArrayObject.getJSONArray("Properties")

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)

            // General Property Attributes
            val propertyCard = PropertyCard.valueOf(jsonObject.getString("PropertyCard"))
            val name = jsonObject.getString("Name")
            val price = jsonObject.getInt("Price")
            val rentJsonArray = jsonObject.getJSONArray("Rent")
            val rent = IntArray(rentJsonArray.length())
            for (j in 0 until rentJsonArray.length()) {
                rent[j] = rentJsonArray.getInt(j)
            }
            val mortgageValue = price / 2
            val unMortgageValue = (mortgageValue * 1.1).roundToInt()
            val propertyType = PropertyType.valueOf(jsonObject.getString("PropertyType"))

            // Color Property
            var color: PropertyColor? = null
            var houseBuyPrice: Int? = null
            var houseSellPrice: Int? = null
            var colorSetPropertyAmount: Int? = null
            var set: Boolean? = null
            var megaSet: Boolean? = null

            // Utility Property
            var utilityType: UtilityType? = null

            when (propertyType) {
                PropertyType.COLOR_PROPERTY -> {
                    color = PropertyColor.valueOf(jsonObject.getString("Color"))
                    houseBuyPrice = jsonObject.getInt("HousePurchasePrice")
                    houseSellPrice = houseBuyPrice / 2
                    colorSetPropertyAmount = jsonObject.getInt("ColorSetPropertyAmount")
                    set = false
                    megaSet = false
                }
                PropertyType.UTILITY -> {
                    utilityType = UtilityType.valueOf(jsonObject.getString("UtilityType"))
                }
                else -> {}
            }

            propertyList.add(
                Property(
                    gameId = gameId,
                    propertyCard = propertyCard,
                    name = name,
                    price = price,
                    rent = rent,
                    mortgageValue = mortgageValue,
                    unMortgageValue = unMortgageValue,
                    mega = mega,
                    propertyType = propertyType,
                    color = color,
                    houseBuyPrice = houseBuyPrice,
                    houseSellPrice = houseSellPrice,
                    colorSetPropertyAmount = colorSetPropertyAmount,
                    set = set,
                    megaSet = megaSet,
                    depotBuyPrice = DEPOT_BUY_PRICE,
                    depotSellPrice = DEPOT_SELL_PRICE,
                    utilityType = utilityType
                )
            )
        }
    } else {
        Log.e(LOG_TAG, "Could not read $jsonFileName from assets")
    }

    return propertyList.toList()
}

fun isDebitCard(msg: String) = CardColor.values().any { it.name == msg }

fun isPropertyCard(msg: String) = PropertyCard.values().any { it.name == msg }