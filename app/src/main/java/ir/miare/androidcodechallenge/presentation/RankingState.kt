package ir.miare.androidcodechallenge.presentation

import ir.miare.androidcodechallenge.data.model.FakeData

data class RankingState(
    val isLoading: Boolean = false,
    val data: List<FakeData> = emptyList(),
    val error: String? = null,
    val sortingMode: Int = -1
)