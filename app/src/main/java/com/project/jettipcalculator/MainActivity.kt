package com.project.jettipcalculator

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.jettipcalculator.components.InputField
import com.project.jettipcalculator.ui.theme.JetTipCalculatorTheme
import com.project.jettipcalculator.widgets.RoundIconButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                MainContent()
            }
        }
    }
}

// Composable function for main surface of the UI
@Composable
fun MyApp(content: @Composable () -> Unit) {
    JetTipCalculatorTheme() {
        // A surface container
        Surface(
            color = MaterialTheme.colors.background
        ) {
            content()
        }
    }
}

// Composable function for the top header which displays the final amount per person
@Composable
fun TopHeader(totalPerPerson: Double = 0.0) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(15.dp),
        shape = RoundedCornerShape(CornerSize(12.dp)),
        color = Color(0xFFE9D7F7)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val total = "%.2f".format(totalPerPerson)
            Text(
                text = "Total Per Person",
                style = MaterialTheme.typography.h5
            )
            Text(
                text = "$$total",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Composable
fun MainContent() {
    Column(modifier = Modifier.padding(12.dp)) {
        BillForm(){

        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BillForm(
    modifier: Modifier = Modifier,
    onValChange: (String) -> Unit = {}
) {
    // remember the value entered by the user
    val totalBillState = remember {
        mutableStateOf("")
    }
    // state to check whether there is a valid value or not
    val isValidBill = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    // remember the state for slider
    val sliderPositionState = remember {
        mutableStateOf(0f)
    }
    TopHeader()
    Surface(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
        border = BorderStroke(width = 1.dp, color = Color.LightGray)
    ) {
        Column(
            modifier = Modifier.padding(6.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            InputField(valueState = totalBillState,
                labelId = "Enter Bill",
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions {
                    if (!isValidBill) return@KeyboardActions
                    onValChange(totalBillState.value.trim())

                    keyboardController?.hide()
                }
            )
            // showing the lower area with buttons and stuff if the value is valid
//            if (validState) {
            // split row
            Row(
                modifier = Modifier.padding(3.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Split",
                    modifier = Modifier.align(
                        alignment = CenterVertically
                    )
                )
                Spacer(modifier = Modifier.width(120.dp))
                Row(
                    modifier = Modifier.padding(horizontal = 3.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    // adding the button for remove and add
                    RoundIconButton(imageVector = Icons.Default.Remove, onClick = {
                        /*TODO*/
                    })

                    Text(
                        text = "2",
                        modifier = Modifier
                            .align(CenterVertically)
                            .padding(start = 9.dp, end = 9.dp)
                    )

                    RoundIconButton(imageVector = Icons.Default.Add, onClick = {
                        /*TODO*/
                    })
                }
            }

            // Tip row
            Row(
                modifier = Modifier.padding(horizontal = 3.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "Tip",
                    modifier = Modifier.align(alignment = CenterVertically)
                )
                Spacer(modifier = Modifier.width(200.dp))
                Text(text = "$33.00")
            }

            // Column for percentage and slider
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "33%")
                Spacer(modifier = Modifier.height(14.dp))

                // Slider
                Slider(value = sliderPositionState.value,
                    onValueChange = { newVal ->
                        // changing the position of the slider
                        sliderPositionState.value = newVal
                    },
                    modifier = Modifier.padding(horizontal = 16.dp),
                    steps = 5,
                    onValueChangeFinished = {

                    }
                )
            }
//            } else {
//                Box() {}
//            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetTipCalculatorTheme {
        MyApp {
            MainContent()
        }
    }
}