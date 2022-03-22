package kg.geektech.quizappaziz.presentation.result

import kg.geektech.quizappaziz.R
import kg.geektech.quizappaziz.core.BaseFragment
import kg.geektech.quizappaziz.databinding.FragmentResultBinding
import kg.geektech.quizappaziz.presentation.game.GameFragment

class ResultFragment : BaseFragment<FragmentResultBinding>() {

    override fun bind(): FragmentResultBinding {
        return FragmentResultBinding.inflate(layoutInflater)
    }

    override fun setupUi() {
        requireActivity().title = getString(R.string.result)
        if (arguments != null) {
            binding.tvCategoryName.text = requireArguments().getString(GameFragment.CATEGORY_NAME)
            binding.tvDifficultyValue.text = requireArguments().getString(GameFragment.DIFFICULTY)
            binding.tvCorrectAnswersValue.text =
                requireArguments().getString(GameFragment.CORRECT_ANSWERS)
            binding.tvResultValue.text = requireArguments().getString(GameFragment.RESULT)
        }
    }

    override fun setupListeners() {
        binding.btnFinish.setOnClickListener {
            navigateFragment(R.id.action_resultFragment_to_startFragment)
        }
    }
}