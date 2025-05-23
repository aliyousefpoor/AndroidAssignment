package ir.miare.androidcodechallenge

import ir.miare.androidcodechallenge.data.model.Player
import ir.miare.androidcodechallenge.presentation.mvi.RankingState
import ir.miare.androidcodechallenge.presentation.ui.RankingScreen
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import ir.miare.androidcodechallenge.data.model.FakeData
import ir.miare.androidcodechallenge.data.model.League
import ir.miare.androidcodechallenge.data.model.Team
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals

@ExperimentalCoroutinesApi
class RankingScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val testFakeData = ArrayList<FakeData>().apply {
        add(
            FakeData(
                league = League(name = "Serie A", country = "Italy", rank = 3, totalMatches = 32),
                players = listOf(
                    Player(
                        name = "Edin Dzeko",
                        team = Team(name = "Inter", rank = 2),
                        totalGoal = 17
                    ),
                    Player(
                        name = "Angel Di Maria",
                        team = Team(name = "Juventus", rank = 3),
                        totalGoal = 9
                    ),
                    Player(
                        name = "Zlatan Ibrahimovic",
                        team = Team(name = "Ac Milan", rank = 1),
                        totalGoal = 17
                    )
                )
            )
        )

        add(
            FakeData(
                league = League(
                    name = "Premier League",
                    country = "England",
                    rank = 1,
                    totalMatches = 38
                ),
                players = listOf(
                    Player(
                        name = "Mohammad Salah",
                        team = Team(name = "Liverpool", rank = 2),
                        totalGoal = 25
                    ), Player(
                        name = "Erling Haaland",
                        team = Team(name = "Man City", rank = 1),
                        totalGoal = 33
                    ), Player(
                        name = "Marcus Rashford",
                        team = Team(name = "Man United", rank = 3),
                        totalGoal = 17
                    )
                )
            )
        )
        add(
            FakeData(
                league =
                League(name = "LaLiga", country = "Spain", rank = 2, totalMatches = 36),
                players = listOf(
                    Player(
                        name = " Antoine Griezmann",
                        team = Team(name = "Atletico", rank = 3),
                        totalGoal = 21
                    ), Player(
                        name = "Karim Benzema",
                        team = Team(name = "Real Madrid", rank = 2),
                        totalGoal = 27
                    ), Player(
                        name = "Robert Lewandowski",
                        team = Team(name = "Barcelona", rank = 1),
                        totalGoal = 23
                    )
                )
            )
        )
    }

    @Test
    fun ranking_screen_displays_sorting_section() {
        val state = RankingState(
            sortingMode = -1,
            isLoading = false,
            error = null,
            data = testFakeData
        )
        var capturedSortOption = -1
        composeTestRule.setContent {
            RankingScreen(
                state = state,
                onPlayerClick = {},
                onSortOptionSelected = { capturedSortOption = it }
            )
        }

        composeTestRule.onNodeWithText("Sorting by:").assertIsDisplayed()

        composeTestRule.onNodeWithText("Select Sorting").performClick()
        composeTestRule.onNodeWithText("Team & league ranking").performClick()

        assertEquals(capturedSortOption, 0)
    }

    @Test
    fun ranking_screen_loading_state_displays_progress_indicator() {
        val state = RankingState(
            sortingMode = -1,
            isLoading = true,
            error = null,
            data = emptyList()
        )
        composeTestRule.setContent {
            RankingScreen(
                state = state,
                onPlayerClick = {},
                onSortOptionSelected = {}
            )
        }

        composeTestRule.onNodeWithText("Sorting by:").assertIsDisplayed()
    }

    @Test
    fun ranking_screen_error_state_displays_error_message() {
        val errorMessage = "Failed to load data"
        val state = RankingState(
            sortingMode = -1,
            isLoading = false,
            error = errorMessage,
            data = emptyList()
        )
        composeTestRule.setContent {
            RankingScreen(
                state = state,
                onPlayerClick = {},
                onSortOptionSelected = {}
            )
        }

        composeTestRule.onNodeWithText("Error: $errorMessage").assertIsDisplayed()
    }

    @Test
    fun ranking_screen_data_state_displays_player_data() {
        val state = RankingState(
            sortingMode = -1,
            isLoading = false,
            error = null,
            data = testFakeData
        )
        var capturedPlayer: Player? = null
        composeTestRule.setContent {
            RankingScreen(
                state = state,
                onPlayerClick = { capturedPlayer = it },
                onSortOptionSelected = {}
            )
        }
        val testPlayer = testFakeData[0].players[1]
        composeTestRule.onNodeWithText(testPlayer.name).assertIsDisplayed()

        composeTestRule.onNodeWithText(testPlayer.name).performClick()

        assertEquals(testPlayer.team.name, capturedPlayer?.team?.name)
        assertEquals(capturedPlayer?.team?.name, "Juventus")
        assertEquals(testPlayer.team.name, "Juventus")

    }
}