package kg.geektech.quizappaziz.presentation.result

import androidx.navigation.fragment.navArgs
import kg.geektech.quizappaziz.R
import kg.geektech.quizappaziz.core.BaseFragment
import kg.geektech.quizappaziz.databinding.FragmentResultBinding

class ResultFragment : BaseFragment<FragmentResultBinding>() {

    private val args: ResultFragmentArgs by navArgs()

    override fun bind(): FragmentResultBinding {
        return FragmentResultBinding.inflate(layoutInflater)
    }

    override fun setupUi() {
        requireActivity().title = getString(R.string.result)

        binding.tvCategoryName.text = args.categoryName
        binding.tvDifficultyValue.text = args.difficulty
        binding.tvCorrectAnswersValue.text = args.correctAnswers
        binding.tvResultValue.text = args.result
    }

    override fun setupListeners() {
        binding.btnFinish.setOnClickListener {
            navController.navigate(ResultFragmentDirections.actionResultFragmentToStartFragment())
        }
    }
}