package ir.miare.androidcodechallenge

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import ir.miare.androidcodechallenge.presentation.PlayerInfoBottomSheet
import ir.miare.androidcodechallenge.presentation.RankingIntent
import ir.miare.androidcodechallenge.presentation.RankingScreen
import ir.miare.androidcodechallenge.presentation.RankingViewModel

@AndroidEntryPoint
class RankingFragment(private val sortingMode: Int) : Fragment() {
    private val viewModel: RankingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                val state = viewModel.state.collectAsStateWithLifecycle().value
                var showBottomSheet by remember { mutableStateOf(false) }
                val playerValue = viewModel.playerState.collectAsStateWithLifecycle().value
                if (showBottomSheet && playerValue != null) {
                    PlayerInfoBottomSheet(player = playerValue, onDismiss = {
                        showBottomSheet = false
                        viewModel.processIntent(RankingIntent.ShowPlayerDetails(null))
                    })
                }
                RankingScreen(
                    state = state,
                    onPlayerClick = { player ->
                        viewModel.processIntent(RankingIntent.ShowPlayerDetails(player))
                        showBottomSheet = true
                    },
                    onSortOptionSelected = { mode ->
                        viewModel.processIntent(RankingIntent.SelectSortOption(mode))
                    }
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.processIntent(RankingIntent.SelectSortOption(sortingMode))
        if (sortingMode == -1) {
            viewModel.processIntent(RankingIntent.LoadData)
        }
    }
}