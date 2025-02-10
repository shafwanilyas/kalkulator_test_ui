package com.login.kalkulator_test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.login.kalkulator_test.ui.theme.CalculatorTheme
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkMode by remember { mutableStateOf(false) }
            CalculatorTheme(darkTheme = isDarkMode) {
                CalculatorApp(isDarkMode, onThemeToggle = { isDarkMode = !isDarkMode })
            }
        }
    }
}

@Composable
fun Appscreen(){
    var isDarkMode by remember{ mutableStateOf(false) }
    CalculatorTheme(darkTheme = isDarkMode) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background)
        {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Switch untuk mengubah mode
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Dark Mode")
                    Switch(
                        checked = isDarkMode,
                        onCheckedChange = { isDarkMode = it }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Panggil layar kalkulator di sini
                CalculatorScreen()

            }
        }
    }
}

@Composable
fun CalculatorScreen(){
    Text(text = "Tampilan kalkulator", modifier = Modifier.padding(16.dp))
}

@Composable
fun CalculatorApp(isDarkMode: Boolean, onThemeToggle: () -> Unit) {
    var input by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    val buttons = listOf(
        listOf("7", "8", "9", "C"),
        listOf("4", "5", "6", "/"),
        listOf("1", "2", "3", "*"),
        listOf("0", ".", "=", "+"),
        listOf("-", "(", ")", "D")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header dengan tema toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Kalkulator",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Switch(checked = isDarkMode, onCheckedChange = { onThemeToggle() }, modifier = Modifier.testTag("switch"))
        }

        // Display input dan hasil
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Text(
                text = input,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
                    .testTag("Input")
            )
            Text(
                text = result,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth().testTag("result")
            )
        }

        // Grid Layout untuk tombol kalkulator
        for (row in buttons) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (btn in row) {
                    CalculatorButton(btn, onClick = {
                        when (btn) {
                            "C" -> {
                                input = ""
                                result = ""
                            }
                            "=" -> {
                                result = try {
                                    evalExpression(input).toString()
                                } catch (e: Exception) {
                                    "Error"
                                }
                            }
                            else -> input += btn
                        }
                    })
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(symbol: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        modifier = Modifier
            .size(80.dp)
            .padding(4.dp)
    ) {
        Text(text = symbol, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary)
    }
}

fun evalExpression(expression: String): Double {
    return try {
        if (expression.isBlank()) return Double.NaN
        if (!expression.matches(Regex("^[0-9+\\-*/().]+$"))) return Double.NaN

        ExpressionBuilder(expression).build().evaluate()
    } catch (e: Exception) {
        Double.NaN
    }
}
@Preview
@Composable
fun PreviewApp(){
    Appscreen()
}