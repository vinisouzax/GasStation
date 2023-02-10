package com.gde.gasstation.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gde.gasstation.R
import com.gde.gasstation.controller.MainController
import com.gde.gasstation.enum.FuelType
import com.gde.gasstation.enum.HasClientCpf
import com.gde.gasstation.model.GasStation
import com.gde.gasstation.model.Sale
import java.util.*


class MainActivity : AppCompatActivity() {
    private var mainController: MainController = MainController()
    private lateinit var tvGasolineQtd: TextView
    private lateinit var tvAlcoholQtd: TextView
    private lateinit var tvDieselQtd: TextView
    private lateinit var tvSales: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeScreen()
        startGasStationSales()
    }

    private fun startGasStationSales() {
        for (i in 0..10) {
            val fuelType = randInt(0, 3)
            val fuelQuantity = randInt(1, 50)

            if (!mainController.hasEnoughFuelByFuelType(fuelType, fuelQuantity)) {
                Toast.makeText(applicationContext, "Not enough fuel", Toast.LENGTH_SHORT).show()
                continue
            }

            val paymentWay = randInt(0, 3)
            val clientCpf = getClientCpf()

            val saleValue = mainController.calculateSaleValue(fuelType, fuelQuantity, paymentWay)

            mainController.updateTankQuantity(fuelType, fuelQuantity)
            updateTankQuantityOnScreen()

            val sale = Sale(fuelType, fuelQuantity, saleValue, paymentWay, clientCpf)
            mainController.registerSale(sale)
            updateScreenWithNewSale(sale)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateTankQuantityOnScreen() {
        tvGasolineQtd.text = "Gasoline: " +
                mainController.getTankQuantityByFuelType(FuelType.GASOLINE).toString()
        tvAlcoholQtd.text = "Alcohol: " +
                mainController.getTankQuantityByFuelType(FuelType.ALCOHOL).toString()
        tvDieselQtd.text = "Diesel: " +
                mainController.getTankQuantityByFuelType(FuelType.DIESEL).toString()
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
        tvDieselQtd = findViewById(R.id.tvDieselQtd)
        tvSales = findViewById(R.id.tvSales)

        tvGasStationName.text = mainController.getGasStationTitle()
        updateTankQuantityOnScreen()
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
}