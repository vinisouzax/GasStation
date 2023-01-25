package com.gde.thinkshieldfuel

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.gde.thinkshieldfuel.model.GasStation
import com.gde.thinkshieldfuel.model.Sale
import java.util.*


class MainActivity : AppCompatActivity() {
    private val gasStation = GasStation()
    private var salesList = arrayListOf<Sale>()

    private lateinit var tvGasolineQtd: TextView
    private lateinit var tvAlcoholQtd: TextView
    private lateinit var tvSales: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeScreen()

        for (i in 0..5) {
            val fuelType = randInt(0, 2)
            val fuelQuantity = randInt(1, 50)
            val paymentWay = randInt(0, 3)
            val saleValue = calculateSaleValue(fuelType, fuelQuantity)
            val clientCpf = getClientCpf()
            registerSale(fuelType, fuelQuantity, paymentWay, saleValue, clientCpf)
        }
    }

    private fun calculateSaleValue(fuelType: Int, fuelQuantity: Int) : Double = when (fuelType) {
        FuelType.GASOLINE.ordinal -> fuelQuantity * gasStation.gasoline.price
        FuelType.ALCOHOL.ordinal -> fuelQuantity * gasStation.alcohol.price
        else -> { throw Exception("Invalid Fuel Type") }
    }

    private fun registerSale(fuelType: Int, fuelQuantity: Int, paymentWay: Int, saleValue: Double,
                             clientCpf: String) {
        val sale = Sale(fuelType, fuelQuantity, saleValue, paymentWay, clientCpf)
        salesList.add(sale)
        updateScreenWithNewSale(sale)
    }

    private fun updateScreenWithNewSale(sale: Sale) {
        val actualText = tvSales.text.toString()
        val newText = actualText + "\n\n" + sale.toString()
        tvSales.text = newText
    }

    @SuppressLint("SetTextI18n")
    private fun initializeScreen() {
        val tvGasStationName = findViewById<TextView>(R.id.tvGasStationName)
        tvGasolineQtd = findViewById(R.id.tvGasolineQtd)
        tvAlcoholQtd = findViewById(R.id.tvAlcoholQtd)
        tvSales = findViewById(R.id.tvSales)

        tvGasStationName.text = gasStation.name + "\n" + gasStation.cnpj
        tvGasolineQtd.text = gasStation.gasoline.tankQuantity.toString()
        tvAlcoholQtd.text = gasStation.alcohol.tankQuantity.toString()
        tvSales.text = ""
    }

    private fun getClientCpf(): String {
        val hasClientCpf = randInt(0, 2) // 0 = Yes, 1 = No
        return if(hasClientCpf == HasClientCpf.Yes.ordinal) "132.892.759-78" else ""
    }

    private fun randInt(from: Int, to: Int) : Int {
        val random = Random()
        return random.nextInt(to - from)
    }

    enum class FuelType {
        GASOLINE, ALCOHOL
    }

    enum class HasClientCpf {
        Yes
    }
}