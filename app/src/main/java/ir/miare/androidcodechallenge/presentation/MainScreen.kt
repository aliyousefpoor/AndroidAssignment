package ir.miare.androidcodechallenge.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun MainScreen(
    viewModel: RankingViewModel = hiltViewModel(),
    sortingMode: Int = -1
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    var showBottomSheet by remember { mutableStateOf(false) }
    val playerValue = viewModel.playerState.collectAsStateWithLifecycle().value
    if (showBottomSheet && playerValue != null) {
        PlayerInfoBottomSheet(player = playerValue, onDismiss = {
            showBottomSheet = false
            viewModel.processIntent(RankingIntent.ShowPlayerDetails(null))
        })
    }
    RankingScreen(
        state = state,
        onPlayerClick = { player ->
            viewModel.processIntent(RankingIntent.ShowPlayerDetails(player))
            showBottomSheet = true
        },
        onSortOptionSelected = { mode ->
            viewModel.processIntent(RankingIntent.SelectSortOption(mode))
        }
    )

    LaunchedEffect(Unit) {
        viewModel.processIntent(RankingIntent.SelectSortOption(sortingMode))
        if (sortingMode == -1) {
            viewModel.processIntent(RankingIntent.LoadData)
        }
    }
}