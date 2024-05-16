package com.gonzalez.blanchard.postresjetpackcompose

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gonzalez.blanchard.postresjetpackcompose.data.DataSource
import com.gonzalez.blanchard.postresjetpackcompose.data.OrderUiState
import com.gonzalez.blanchard.postresjetpackcompose.ui.viewmodel.OrderViewModel
import com.gonzalez.blanchard.postresjetpackcompose.ui.views.DeliveryScreen
import com.gonzalez.blanchard.postresjetpackcompose.ui.views.SelectOptionScreen
import com.gonzalez.blanchard.postresjetpackcompose.ui.views.StartScreen
import com.gonzalez.blanchard.postresjetpackcompose.ui.views.SummaryScreen
import androidx.lifecycle.viewmodel.compose.viewModel

enum class DessertScreen(
   val title: String
){
    Start(title = "Inicio"),
    Flavor(title = "Sabor"),
    Pickup(title = "Delivery"),
    Summary(title = "Resumen"),
}

@Composable
fun DessertApp(
    viewModel: OrderViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
){
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = DessertScreen.valueOf(
        backStackEntry?.destination?.route ?: DessertScreen.Start.name
    )

    Scaffold(
        topBar = {
            DessertAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }
    ){ innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = DessertScreen.Start.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ){

            composable(route = DessertScreen.Start.name){
                StartScreen(
                    quiantityOptions = DataSource.quantityOptions,
                    onNextButtonClicked = {
                        viewModel.setQuantity(it)
                        navController.navigate(DessertScreen.Flavor.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),

                )
            }

            composable(route = DessertScreen.Flavor.name){
                SelectOptionScreen(
                    subtotal = uiState.price,
                    options = listOf("Vainilla", "Chocolate", "Fresa"),
                    modifier = Modifier.fillMaxHeight(),
                    onNextButtonClicked = {
                        navController.navigate(DessertScreen.Pickup.name)
                    },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel = viewModel, navController)
                    },
                    onSelectionChanged = { flavorName ->
                        viewModel.setFlavor(flavorName)
                    },
                )
            }

            composable(route = DessertScreen.Pickup.name){
                DeliveryScreen(
                    subtotal = uiState.price,
                    options = listOf("Delivery", "Para llevar"),
                    modifier = Modifier.fillMaxHeight(),
                    onNextButtonClicked = {
                        navController.navigate(DessertScreen.Summary.name)
                    },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel = viewModel, navController)
                    },
                    onSelectionChanged = {
                        viewModel.setDelivery(it)
                    }
                )
            }

            composable(route = DessertScreen.Summary.name){
                val context = LocalContext.current
                SummaryScreen(
                    orderUiState = uiState,
                    onSendButtonClicked = { subject: String, summary: String ->
                         shareOrder(context, subject, summary)
                    },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel = viewModel, navController)
                    },
                    modifier = Modifier.fillMaxHeight()
                )
            }
        }
    }

}

private fun cancelOrderAndNavigateToStart(
    viewModel: OrderViewModel,
    navController: NavHostController
){
    viewModel.resetOrder()
    navController.popBackStack(DessertScreen.Start.name, inclusive = false)
}


private fun shareOrder(
    context: Context,
    subject: String,
    summary: String
){
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, summary)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            "Nueva orden de postre"
        )
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DessertAppBar(
    currentScreen: DessertScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
){
    TopAppBar(
        title = {
            Text(text = currentScreen.title)
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if(canNavigateBack){
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back button"
                    )
                }
            }
        }

    )
}