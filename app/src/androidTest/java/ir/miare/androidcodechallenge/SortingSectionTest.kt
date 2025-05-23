package ir.miare.androidcodechallenge

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import ir.miare.androidcodechallenge.presentation.ui.SortingSection
import org.junit.Rule
import org.junit.Test

class SortingSectionTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun when_rendered_shows_current_sorting_option() {
        composeTestRule.setContent {
            SortingSection(
                selectedOption = 1,
                onOptionSelected = {}
            )
        }

        composeTestRule.onNodeWithText("Most goals scored by a player").assertExists()
    }

    @Test
    fun when_option_selected_calls_callback() {
        var selectedOption: Int? = null

        composeTestRule.setContent {
            SortingSection(
                selectedOption = -1,
                onOptionSelected = { selectedOption = it }
            )
        }

        composeTestRule.onNodeWithText("Select Sorting").performClick()

        composeTestRule.onNodeWithText("Team & league ranking").performClick()

        assert(selectedOption == 0)
    }
}