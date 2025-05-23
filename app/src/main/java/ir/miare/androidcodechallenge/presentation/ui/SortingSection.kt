package ir.miare.androidcodechallenge.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun SortingSection(
    selectedOption: Int,
    onOptionSelected: (Int) -> Unit
) {
    val options = listOf(
        "None" to -1,
        "Team & league ranking" to 0,
        "Most goals scored by a player" to 1,
        "Average goal per match in a league" to 2
    )
    var selectedText = options.find { it.second == selectedOption }?.first ?: "Select Sorting"
//    var SortingValue by remember { mutableStateOf("Sorting") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(top = 16.dp, bottom = 16.dp)
    ) {
        Text(
            text = "Sorting by:",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 8.dp, end = 8.dp)
        ) {
            DropdownTextField(
                options,
                label = "Select Sorting",
                selectedOption = selectedText,
                onOptionSelected = {
                    selectedText = it.first
                    onOptionSelected.invoke(it.second)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            )
        }
    }
}