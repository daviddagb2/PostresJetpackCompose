package com.gonzalez.blanchard.postresjetpackcompose.data

data class OrderUiState(
    val quantity: Int = 0,
    val flavor: String = "",
    val price: String = "",
    val selectedPickup: String = "",
    val pickupOptions: List<String> = listOf()
)
