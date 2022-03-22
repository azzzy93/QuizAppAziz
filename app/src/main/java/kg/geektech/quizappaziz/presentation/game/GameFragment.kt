package kg.geektech.quizappaziz.presentation.game

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kg.geektech.quizappaziz.R
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
    private var categoryName = ""
    private var difficulty = ""
    private var currentPosition = 0
    private var correctAnswer = 0
    private val viewModel: GameViewModel by viewModels()
    private val adapter: GameAdapter by lazy {
        GameAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun bind(): FragmentGameBinding {
        return FragmentGameBinding.inflate(layoutInflater)
    }

    override fun setupListeners() {
        adapter.onItemClick = { pos, answer ->

            currentPosition = pos + 1
            correctAnswer += answer

            lifecycleScope.launch {
                delay(2000)
                if (adapter.currentList.size - 1 == pos) {
                    openResultFragment()
                } else {
                    binding.rvGame.layoutManager?.scrollToPosition(currentPosition)
                }
            }

        }
    }

    private fun openResultFragment() {
        val bundle = Bundle()
        val correctAnswers = "$correctAnswer/$questionAmount"
        val resultPercent: Double = (correctAnswer.toDouble() / questionAmount.toDouble()) * 100
        val result = "${resultPercent.toInt()}%"
        bundle.apply {
            putString(CATEGORY_NAME, categoryName)
            putString(DIFFICULTY, difficulty)
            putString(CORRECT_ANSWERS, correctAnswers)
            putString(RESULT, result)
        }
        navigateFragment(R.id.action_gameFragment_to_resultFragment, bundle)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                if (currentPosition > 0) {
                    currentPosition -= 1
                    binding.rvGame.layoutManager?.scrollToPosition(currentPosition)
                }
                true
            }
            else ->
                super.onOptionsItemSelected(item)
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
                categoryName = it
                requireActivity().title = categoryName
            }
            questionAmount = requireArguments().getInt(StartFragment.QUESTIONS_AMOUNT)
            requireArguments().getString(StartFragment.DIFFICULTY)?.let {
                difficulty = it
            }
        }
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

    companion object {
        const val CATEGORY_NAME = "CATEGORY_NAME"
        const val DIFFICULTY = "DIFFICULTY"
        const val CORRECT_ANSWERS = "CORRECT_ANSWERS"
        const val RESULT = "RESULT"
    }
}