package kg.geektech.quizappaziz.presentation.game

import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kg.geektech.quizappaziz.core.BaseFragment
import kg.geektech.quizappaziz.databinding.FragmentGameBinding
import kg.geektech.quizappaziz.presentation.start.StartFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class GameFragment : BaseFragment<FragmentGameBinding>() {

    private var maxCount = 0
    private var categoryId = 0
    private var difficulty = ""
    private var currentCount = 1

    private val viewModel: GameViewModel by viewModels()

    override fun bind(): FragmentGameBinding {
        return FragmentGameBinding.inflate(layoutInflater)
    }

    override fun setupListeners() {
    }

    override fun setupObservers() {
        viewModel.fetchQsts(maxCount, categoryId, difficulty)
        viewModel.qstsList.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).onEach {
            Log.e("Aziz", "setupObservers: $it")
        }.launchIn(lifecycleScope)
        viewModel.state.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).onEach {
            handleState(it)
        }.launchIn(lifecycleScope)
    }

    private fun handleState(state: GameViewModel.GameFragmentState) {
        when (state) {
            is GameViewModel.GameFragmentState.IsLoading -> {
                binding.pbGame.isVisible = state.isLoading
            }
            is GameViewModel.GameFragmentState.ShowToast -> {
                Toast.makeText(requireContext(), "Message: ${state.message}", Toast.LENGTH_SHORT)
                    .show()
            }
            is GameViewModel.GameFragmentState.Init -> Unit
        }
    }

    override fun setupUi() {
        if (arguments != null) {
            maxCount = requireArguments().getInt(StartFragment.QUESTIONS_AMOUNT)
            categoryId = requireArguments().getInt(StartFragment.CATEGORY_ID)
            difficulty = requireArguments().getString(StartFragment.DIFFICULTY)!!
        }
        changeProgress()
    }

    private fun changeProgress() {
        val count = "$currentCount/$maxCount"
        binding.tvProgressCounter.text = count
        binding.progressLinear.max = maxCount
        binding.progressLinear.progress = currentCount
    }

}