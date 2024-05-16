package com.gonzalez.blanchard.postresjetpackcompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.gonzalez.blanchard.postresjetpackcompose.data.OrderUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.NumberFormat

private const val PRICE_PER_DESSERT = 2.0
private const val PRICE_FOR_DELIVERY = 3.0

class OrderViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(OrderUiState(pickupOptions = listOf("Delivery", "Para Llevar")))
    val uiState: StateFlow<OrderUiState> =_uiState.asStateFlow()

    fun setQuantity(numberOfDesserts: Int){
        _uiState.update { currentState ->
            currentState.copy(
                quantity = numberOfDesserts,
                price = calculatePrice(quantity = numberOfDesserts)
            )
        }
    }

    fun setFlavor(desiredFlavor: String){
        _uiState.update { currentState ->
            currentState.copy(
                flavor = desiredFlavor
            )
        }
    }

    fun setDelivery(selectedPickup: String){
        _uiState.update { currentState ->
            currentState.copy(
                selectedPickup = selectedPickup,
                price = calculatePrice(delivery = selectedPickup)
            )
        }
    }

    fun resetOrder(){
        _uiState.value = OrderUiState(pickupOptions = listOf("Delivery", "Para Llevar"))
    }

    private fun calculatePrice(
        quantity: Int = _uiState.value.quantity,
        delivery: String = _uiState.value.selectedPickup
    ): String {
        var calculatedPrice = quantity * PRICE_PER_DESSERT
        if (delivery == "Delivery") {
            calculatedPrice += PRICE_FOR_DELIVERY
        }
        return NumberFormat.getCurrencyInstance().format(calculatedPrice)
    }

}