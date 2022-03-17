package kg.geektech.quizappaziz.presentation.game

import kg.geektech.quizappaziz.databinding.FragmentGameBinding
import kg.geektech.quizappaziz.domain.common.base.BaseFragment
import kg.geektech.quizappaziz.presentation.start.StartFragment

class GameFragment : BaseFragment<FragmentGameBinding>() {

    private var maxCount = 0
    private var categoryId = 0
    private var difficulty = ""
    private var currentCount = 1

    override fun bind(): FragmentGameBinding {
        return FragmentGameBinding.inflate(layoutInflater)
    }

    override fun setupListeners() {
    }

    override fun setupObservers() {
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