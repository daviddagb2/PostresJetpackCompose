package com.gonzalez.blanchard.postresjetpackcompose.ui.views

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gonzalez.blanchard.postresjetpackcompose.R
import com.gonzalez.blanchard.postresjetpackcompose.data.DataSource
import com.gonzalez.blanchard.postresjetpackcompose.ui.theme.PostresJetpackComposeTheme

@Composable
fun StartScreen(
    quiantityOptions: List<Pair<Int, Int>>,
    onNextButtonClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.SpaceAround
    ) {

        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
            Spacer(modifier = modifier.height(5.dp))
            Image(
               painter = painterResource(id = R.drawable.dessert),
                contentDescription = "",
                modifier = Modifier.width(300.dp)
            )
            Spacer(modifier = modifier.height(5.dp))
            Text(text = "Ordenar un postre",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = modifier.height(20.dp))
        }

        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            quiantityOptions.forEach{ item ->
                SelectQuantityButton(
                    labelResourceId = item.first,
                    onClick = { onNextButtonClicked(item.second) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }


    }
}


@Composable
fun SelectQuantityButton(
    @StringRes labelResourceId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Button(onClick = onClick,
        modifier = modifier.widthIn(min = 250.dp)
        ) {
        Text(text = stringResource(id = labelResourceId))
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewStartScreen(){
    PostresJetpackComposeTheme {
        StartScreen(quiantityOptions = DataSource.quantityOptions,
            onNextButtonClicked = {},
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        )
    }
}