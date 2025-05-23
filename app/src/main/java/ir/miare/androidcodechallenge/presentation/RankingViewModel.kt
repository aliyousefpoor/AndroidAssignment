package ir.miare.androidcodechallenge.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.miare.androidcodechallenge.data.model.FakeData
import ir.miare.androidcodechallenge.data.model.Player
import ir.miare.androidcodechallenge.domain.usecase.GetRankingDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val getRankingDataUseCase: GetRankingDataUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RankingState())
    val state: StateFlow<RankingState> = _state.asStateFlow()

    private val _playerState = MutableStateFlow<Player?>(null)
    val playerState: StateFlow<Player?> = _playerState.asStateFlow()

    fun processIntent(intent: RankingIntent) {
        when (intent) {
            is RankingIntent.LoadData -> loadData()
            is RankingIntent.ShowPlayerDetails -> {
                _playerState.value = intent.player
            }
            is RankingIntent.SelectSortOption -> updateSortingMode(intent.sortingMode)
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                getRankingDataUseCase.invoke().collectLatest { data ->
                    data?.let {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            data = applySorting(data, _state.value.sortingMode),
                            error = null
                        )
                    }
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    private fun updateSortingMode(sortingMode: Int) {
        viewModelScope.launch {
            val sortedData = applySorting(_state.value.data, sortingMode)
            _state.value = _state.value.copy(sortingMode = sortingMode, data = sortedData)
        }
    }

    private fun applySorting(data: List<FakeData>, sortingMode: Int): List<FakeData> {
        return when (sortingMode) {
            0 -> {
                data.sortedBy { it.league.name }.map { fakeData ->
                    fakeData.copy(players = fakeData.players.sortedBy { it.team.rank })
                }
            }

            1 -> {
                data.map { fakeData ->
                    fakeData.copy(players = fakeData.players.sortedByDescending {
                        it.totalGoal ?: 0
                    })
                }
            }

            2 -> {
                data.sortedByDescending { it.league.rank.toFloat() ?: 0f }
            }

            else -> data
        }
    }
}