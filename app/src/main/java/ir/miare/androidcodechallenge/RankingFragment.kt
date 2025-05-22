package ir.miare.androidcodechallenge

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
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
                RankingScreen(
                    state = state,
                    onPlayerClick = { player ->
                        viewModel.processIntent(RankingIntent.ShowPlayerDetails(player))
                        PlayerInfoBottomSheet(player).show(parentFragmentManager, "")
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