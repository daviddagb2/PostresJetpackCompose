package com.gonzalez.blanchard.postresjetpackcompose.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FormattedPriceLabel(
    subtotal: String,
    modifier: Modifier = Modifier
){
    Text(
        text = "Subtotal: $subtotal",
        modifier = modifier,
        style = MaterialTheme.typography.headlineSmall
    )
}