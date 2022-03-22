package kg.geektech.quizappaziz.presentation.game

import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kg.geektech.quizappaziz.core.BaseFragment
import kg.geektech.quizappaziz.databinding.FragmentGameBinding
import kg.geektech.quizappaziz.presentation.start.StartFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GameFragment : BaseFragment<FragmentGameBinding>() {

    private var questionAmount = 1
    private var categoryId = -1
    private var difficulty = ""
    private val viewModel: GameViewModel by viewModels()
    private val adapter: GameAdapter by lazy {
        GameAdapter()
    }

    override fun bind(): FragmentGameBinding {
        return FragmentGameBinding.inflate(layoutInflater)
    }

    override fun setupListeners() {
        adapter.onItemClick = {
            if (adapter.currentList.size-1 > it) {
                lifecycleScope.launch {
                    delay(2000)
                    binding.rvGame.layoutManager?.scrollToPosition(1 + it)
                    changeProgress(2 + it)
                }
            }
        }
    }

    override fun setupObservers() {
        if (categoryId == -1 && difficulty != "any difficulty") {
            viewModel.fetchQsts(amount = questionAmount, difficulty = difficulty)
            Log.e("Aziz", "without category")
        } else if (difficulty == "any difficulty" && categoryId != -1) {
            viewModel.fetchQsts(amount = questionAmount, categoryId = categoryId)
            Log.e("Aziz", "without difficulty")
        } else if (categoryId == -1 && difficulty == "any difficulty") {
            viewModel.fetchQsts(amount = questionAmount)
            Log.e("Aziz", "without category and difficulty")
        } else {
            viewModel.fetchQsts(
                amount = questionAmount,
                categoryId = categoryId,
                difficulty = difficulty
            )
            Log.e("Aziz", "with ALL")
        }

        viewModel.qstsList.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).onEach {
            adapter.submitList(it)
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
            categoryId = requireArguments().getInt(StartFragment.CATEGORY_ID)
            requireArguments().getString(StartFragment.CATEGORY_NAME)?.let {
                requireActivity().title = it
            }
            questionAmount = requireArguments().getInt(StartFragment.QUESTIONS_AMOUNT)
            requireArguments().getString(StartFragment.DIFFICULTY)?.let {
                difficulty = it
            }
        }
        changeProgress(1)
        initRv()
    }

    private fun initRv() {
        binding.rvGame.apply {
            layoutManager = object : LinearLayoutManager(requireContext(), HORIZONTAL, false) {
                override fun canScrollHorizontally(): Boolean {
                    return false
                }
            }
            adapter = this@GameFragment.adapter
        }
    }

    private fun changeProgress(currentCount: Int) {
        val count = "$currentCount/$questionAmount"
        binding.tvProgressCounter.text = count
        binding.progressLinear.max = questionAmount
        binding.progressLinear.progress = currentCount
    }
}