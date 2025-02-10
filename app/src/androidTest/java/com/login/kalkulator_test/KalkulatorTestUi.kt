package com.login.kalkulator_test

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.login.kalkulator_test.ui.theme.CalculatorTheme
import org.junit.Rule
import org.junit.Test

class KalkulatorTestUi {
    @get: Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testValidCalculation() {
        composeTestRule.setContent {
            CalculatorTheme {
                CalculatorApp(isDarkMode = false, onThemeToggle = {})
            }
        }

        // Masukkan angka dan operator: 5 + 3 =
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("3").performClick()
        composeTestRule.onNodeWithText("=").performClick()

        composeTestRule.waitForIdle()
        // Periksa apakah hasilnya benar (8)
        composeTestRule.onNodeWithTag("result")
            .assertTextEquals("8.0")
    }

    @Test
    fun testInvalidCalculator() {
        composeTestRule.setContent {
            CalculatorTheme {
                CalculatorApp(isDarkMode = true, onThemeToggle ={} )
            }

        }

        composeTestRule.onNode(hasText("5") and hasClickAction()).performClick()
        composeTestRule.onNodeWithText("/").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("=").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("result")
            .assertTextEquals("NaN")

    }
    @Test
    fun testDarkModeToggle() {
        composeTestRule.setContent {
            var isDarkMode by remember { mutableStateOf(false) } // Perubahan penting!
            CalculatorTheme(darkTheme = isDarkMode) {
                CalculatorApp(isDarkMode = isDarkMode, onThemeToggle = { isDarkMode = !isDarkMode })
            }
        }

        val switch = composeTestRule.onNodeWithTag("switch")

        // Pastikan awalnya dalam mode Light (Switch = Off)
        switch.assertIsOff()

        // Klik switch untuk mengaktifkan Dark Mode
        switch.performClick()

        // Tunggu hingga UI memperbarui tampilan
        composeTestRule.waitForIdle()

        // Periksa apakah Dark Mode sekarang aktif (Switch = On)
        switch.assertIsOn()
    }

    @Test
    fun komponenapp(){
        composeTestRule.setContent {
            CalculatorTheme {
                CalculatorApp(isDarkMode = false, onThemeToggle = {})
            }
        }

        composeTestRule.onNode(hasText("1") and hasClickAction()).assertHasClickAction()
        composeTestRule.onNode(hasText("2") and hasClickAction()).assertHasClickAction()
        composeTestRule.onNode(hasText("3") and hasClickAction()).assertHasClickAction()
        composeTestRule.onNode(hasText("4") and hasClickAction()).assertHasClickAction()
        composeTestRule.onNode(hasText("5") and hasClickAction()).assertHasClickAction()
        composeTestRule.onNode(hasText("6") and hasClickAction()).assertHasClickAction()
        composeTestRule.onNode(hasText("7") and hasClickAction()).assertHasClickAction()
        composeTestRule.onNode(hasText("8") and hasClickAction()).assertHasClickAction()
        composeTestRule.onNode(hasText("9") and hasClickAction()).assertHasClickAction()
        composeTestRule.onNode(hasText("=") and hasClickAction()).assertHasClickAction()
        composeTestRule.onNode(hasText("+") and hasClickAction()).assertHasClickAction()
        composeTestRule.onNode(hasText("C") and hasClickAction()).assertHasClickAction()
        composeTestRule.onNode(hasText(".") and hasClickAction()).assertHasClickAction()
        composeTestRule.onNode(hasText("/") and hasClickAction()).assertHasClickAction()
        composeTestRule.onNode(hasText("*") and hasClickAction()).assertHasClickAction()
        composeTestRule.onNode(hasText("-") and hasClickAction()).assertHasClickAction()
        composeTestRule.onNode(hasText("(") and hasClickAction()).assertHasClickAction()
        composeTestRule.onNode(hasText(")") and hasClickAction()).assertHasClickAction()
        composeTestRule.onNode(hasText("D") and hasClickAction()).assertHasClickAction()
        composeTestRule.onNodeWithTag("switch").assertHasClickAction()
    }


}
