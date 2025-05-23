package ir.miare.androidcodechallenge.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.miare.androidcodechallenge.data.model.Player


@Composable
fun RankingScreen(
    state: RankingState,
    onPlayerClick: (Player) -> Unit,
    onSortOptionSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        SortingSection(
            selectedOption = state.sortingMode,
            onOptionSelected = onSortOptionSelected
        )
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (state.error != null) {
            Text(text = "Error: ${state.error}", modifier = Modifier.padding(16.dp))
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.data) { fakeData ->
                    DataItem(fakeData = fakeData, onPlayerClick = { player ->
                        onPlayerClick.invoke(player)
                    })
                }
            }
        }
    }
}
