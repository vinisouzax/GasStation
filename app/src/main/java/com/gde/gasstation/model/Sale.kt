package com.gde.gasstation.model

// Fuel Types -> 0 = Gasoline, 1 = Alcohol
// Payment Ways -> 0 = Money, 1 = Credit, 2 = Debit
class Sale(var fuelType: Int = 0, var fuelQuantity : Int = 0, var saleValue : Double = 0.0,
           var paymentWay: Int = 0, var clientCpf: String) {
    override fun toString(): String {
        return "Sale(fuelType=$fuelType, fuelQuantity=$fuelQuantity, " +
                "saleValue=$saleValue, paymentWay=$paymentWay, clientCpf='$clientCpf')"
    }
}