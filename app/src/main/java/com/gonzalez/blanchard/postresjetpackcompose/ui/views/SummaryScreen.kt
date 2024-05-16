package com.gonzalez.blanchard.postresjetpackcompose.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gonzalez.blanchard.postresjetpackcompose.data.OrderUiState
import com.gonzalez.blanchard.postresjetpackcompose.ui.components.FormattedPriceLabel

@Composable
fun SummaryScreen(
    orderUiState: OrderUiState,
    onCancelButtonClicked: () -> Unit,
    onSendButtonClicked: (String, String) -> Unit,
    modifier: Modifier = Modifier
){

    val numberOfDessets = orderUiState.quantity

    val orderSummary = "Cantidad: ${orderUiState.quantity} \n " +
            "Sabor: ${orderUiState.flavor} \n " +
            "Entrega: ${orderUiState.selectedPickup} \n " +
            "Total: $ ${orderUiState.price} \n\n " +
            "Muchas gracias"

    val items = listOf(
        Pair("Cantidad", orderUiState.quantity),
        Pair("Sabor", orderUiState.flavor),
        Pair("Entrega", orderUiState.selectedPickup),
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items.forEach{ item ->
                Text(text = item.first.uppercase())
                Text(text = item.second.toString(), fontWeight = FontWeight.Bold )
                Divider(
                    thickness = 2.dp
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            FormattedPriceLabel(subtotal = orderUiState.price, modifier = Modifier.align(Alignment.End)
            )
        }

        Row (
            modifier = Modifier.padding(10.dp)
        ){
            Column (
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ){
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                    onSendButtonClicked("NUEVA ORDEN", orderSummary)
                }) {
                    Text(text = "Enviar")
                }

                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onCancelButtonClicked)
                {
                    Text(text = "Cancelar")
                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSummaryScreen(){
    SummaryScreen(
        orderUiState = OrderUiState(0, "Test", "300", "Para llevar"),
        onSendButtonClicked = {
            subject: String, summary: String ->
        },
        onCancelButtonClicked = {},
        modifier = Modifier.fillMaxHeight()
    )
}