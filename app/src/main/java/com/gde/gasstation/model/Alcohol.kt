package com.gde.gasstation.model

class Alcohol : Fuel() {
    override var price: Double = 4.23
    override var tankQuantity: Int = 11000
}