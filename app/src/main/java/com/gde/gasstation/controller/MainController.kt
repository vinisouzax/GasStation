package com.gde.gasstation.controller

import com.gde.gasstation.enum.FuelType
import com.gde.gasstation.enum.PaymentWay
import com.gde.gasstation.model.GasStation
import com.gde.gasstation.model.Sale

class MainController {
    private val gasStation = GasStation()
    private var salesList = arrayListOf<Sale>()

    fun calculateSaleValue(fuelType: Int, fuelQuantity: Int, paymentWay: Int) : Double = when (fuelType) {
        FuelType.GASOLINE.ordinal -> (fuelQuantity * gasStation.gasoline.price) +
                (fuelQuantity * gasStation.gasoline.price * getTaxFromPaymentWay(paymentWay))
        FuelType.ALCOHOL.ordinal -> (fuelQuantity * gasStation.alcohol.price) +
                (fuelQuantity * gasStation.alcohol.price * getTaxFromPaymentWay(paymentWay))
        FuelType.DIESEL.ordinal -> (fuelQuantity * gasStation.diesel.price) +
                (fuelQuantity * gasStation.diesel.price * getTaxFromPaymentWay(paymentWay))
        else -> { throw Exception("Invalid Fuel Type") }
    }

    fun updateTankQuantity(fuelType: Int, fuelQuantity: Int) {
        when (fuelType) {
            FuelType.GASOLINE.ordinal -> {
                gasStation.gasoline.tankQuantity -= fuelQuantity
            }
            FuelType.ALCOHOL.ordinal -> {
                gasStation.alcohol.tankQuantity -= fuelQuantity
            }
            FuelType.DIESEL.ordinal -> {
                gasStation.diesel.tankQuantity -= fuelQuantity
            }
            else -> {
                throw Exception("Invalid Fuel Type")
            }
        }
    }

    fun registerSale(sale: Sale) {
        salesList.add(sale)
    }

    fun getTankQuantityByFuelType(fuelType: FuelType) : Int = when (fuelType) {
        FuelType.GASOLINE -> gasStation.gasoline.tankQuantity
        FuelType.ALCOHOL -> gasStation.alcohol.tankQuantity
        FuelType.DIESEL -> gasStation.diesel.tankQuantity
    }

    fun getGasStationTitle() : String {
        return gasStation.name + "\n" + gasStation.cnpj
    }

    fun hasEnoughFuelByFuelType(fuelType: Int, fuelNeeded: Int) : Boolean = when(fuelType) {
        FuelType.GASOLINE.ordinal -> fuelNeeded <= gasStation.gasoline.tankQuantity
        FuelType.ALCOHOL.ordinal -> fuelNeeded <= gasStation.alcohol.tankQuantity
        FuelType.DIESEL.ordinal -> fuelNeeded <= gasStation.diesel.tankQuantity
        else -> {
            throw Exception("Invalid Fuel Type")
        }
    }

    private fun getTaxFromPaymentWay(paymentWay: Int) : Double = when (paymentWay) {
        PaymentWay.MONEY.ordinal -> 0.0
        PaymentWay.CREDIT.ordinal -> 0.017
        PaymentWay.DEBIT.ordinal -> 0.013
        else -> { throw Exception("Invalid Payment Type") }
    }
}