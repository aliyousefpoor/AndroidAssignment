package ir.miare.androidcodechallenge.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.miare.androidcodechallenge.domain.usecase.GetRankingDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val getRankingDataUseCase: GetRankingDataUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RankingState())
    val state: StateFlow<RankingState> = _state

    fun processIntent(intent: RankingIntent) {
        when (intent) {
            is RankingIntent.LoadData -> loadData()
            is RankingIntent.ShowPlayerDetails -> {}
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _state.value = RankingState(isLoading = true)
            getRankingDataUseCase.invoke().collectLatest { data ->
                data?.let {
                    _state.value = RankingState(data = data, isLoading = false)
                }
            }
        }
    }
}