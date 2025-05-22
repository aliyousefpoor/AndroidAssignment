package ir.miare.androidcodechallenge.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
                state.data.forEach { fakeData ->
                    item {
                        LeagueItem(
                            leagueName = fakeData.league.name.takeIf { it.isNotEmpty() } ?: "Unknown League",
                            leagueCountry = fakeData.league.country.takeIf { it.isNotEmpty() } ?: "Unknown Country"
                        )
                    }
                    items(fakeData.players.size) { index ->
                        PlayerItem(
                            player = fakeData.players[index],
                            onClick = { onPlayerClick(fakeData.players[index]) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SortingSection(
    selectedOption: Int,
    onOptionSelected: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(top = 16.dp, bottom = 16.dp)
    ) {
        Text(
            text = "Sorting by:",
            style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 8.dp, end = 8.dp)
        ) {
            val options = listOf(
                "Team & league ranking" to 0,
                "Most goals scored by a player" to 1,
                "Average goal per match in a league" to 2,
                "None" to -1
            )
            options.forEach { (text, mode) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (mode == selectedOption),
                            onClick = { onOptionSelected(mode) }
                        )
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (mode == selectedOption),
                        onClick = { onOptionSelected(mode) }
                    )
                    Text(
                        text = text,
                        style = MaterialTheme.typography.body2.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        ),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun LeagueItem(leagueName: String, leagueCountry: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = leagueName, style = MaterialTheme.typography.body2)
            Text(text = leagueCountry,  style = MaterialTheme.typography.body2)
        }
    }
}

@Composable
fun PlayerItem(player: Player, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable { onClick() },
        elevation =2.dp
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = player.name.takeIf { it.isNotEmpty() } ?: "Unknown Player",
                    style = MaterialTheme.typography.body2
                )
                Text(
                    text = player.team.name.takeIf { it.isNotEmpty() } ?: "Unknown Team",
                    style = MaterialTheme.typography.body2
                )
            }
            Text(
                text = player.team.rank.toString(),
                style = MaterialTheme.typography.body2
            )
        }
    }
}