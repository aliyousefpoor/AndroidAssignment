package ir.miare.androidcodechallenge.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.miare.androidcodechallenge.data.model.Player

@Composable
fun PlayerItem(player: Player, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable { onClick() },
        elevation = 2.dp
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