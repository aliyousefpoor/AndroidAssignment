package ir.miare.androidcodechallenge.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownTextField(
    options: List<Pair<String, Int>>,
    selectedOption: String,
    onOptionSelected: (Pair<String, Int>) -> Unit,
    label: String = "", modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            androidx.compose.material3.OutlinedTextField(
                value = selectedOption,
                onValueChange = {},
                readOnly = true,
                label = {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.body2
                    )
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                placeholder = {
                    Text(
                        text = "Select Sorting",
                        style = MaterialTheme.typography.body2
                    )
                },
                modifier = modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Blue,
                    unfocusedIndicatorColor = Color.Gray,
                    errorIndicatorColor = Color.Red,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    errorContainerColor = Color.White,
                    focusedTextColor = Color.Black,
                    focusedPlaceholderColor = Color.Blue,
                    unfocusedPlaceholderColor = Color.Gray,
                    focusedLabelColor = Color.Blue,
                    unfocusedLabelColor = Color.Gray,
                ),
                textStyle = MaterialTheme.typography.body2
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier.background(color = Color.White)
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = option.first,
                                fontSize = 16.sp,
                                style = MaterialTheme.typography.body2,
                                color = if (selectedOption == option.first) Color.White else Color.Gray,
                                textAlign = TextAlign.Start
                            )
                        },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        }, modifier = modifier.background(
                            color = if (selectedOption == option.first) Color.Blue else Color.White
                        )
                    )
                }
            }
        }
    }
}