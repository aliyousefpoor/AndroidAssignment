package ir.miare.androidcodechallenge.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ir.miare.androidcodechallenge.data.model.FakeData
import ir.miare.androidcodechallenge.data.model.Player

@Composable
fun DataItem(fakeData: FakeData, onPlayerClick: (Player) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
            LeagueItem(
                leagueName = fakeData.league.name.takeIf { it.isNotEmpty() } ?: "Unknown League",
                leagueCountry = fakeData.league.country.takeIf { it.isNotEmpty() }
                    ?: "Unknown Country"
            )
            fakeData.players?.forEachIndexed { index, player ->
                PlayerItem(
                    player = fakeData.players[index],
                    onClick = { onPlayerClick.invoke(fakeData.players[index]) }
                )
                if (index != fakeData.players.size - 1) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(color = Color.Gray)
                    )
                }
            }
        }
    }
}