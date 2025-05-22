package ir.miare.androidcodechallenge.presentation

import ir.miare.androidcodechallenge.data.model.Player

sealed class RankingIntent {
    data object LoadData : RankingIntent()
    data class ShowPlayerDetails(val player: Player) : RankingIntent()
    data class SelectSortOption(val sortingMode: Int) : RankingIntent()
}