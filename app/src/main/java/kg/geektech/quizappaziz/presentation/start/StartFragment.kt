package kg.geektech.quizappaziz.presentation.start

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import kg.geektech.quizappaziz.R
import kg.geektech.quizappaziz.core.BaseFragment
import kg.geektech.quizappaziz.databinding.FragmentStartBinding
import kg.geektech.quizappaziz.domain.start.entity.CategoryEntity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class StartFragment : BaseFragment<FragmentStartBinding>() {

    private val viewModel: StartViewModel by viewModels()

    override fun bind(): FragmentStartBinding {
        return FragmentStartBinding.inflate(layoutInflater)
    }

    override fun setupListeners() {
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                binding.tvQuestionsAmountValue.text = p1.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}

        })
        binding.btnStart.setOnClickListener {
            openGameFragment()
        }
    }

    private fun openGameFragment() {
        val category = binding.spinnerCategory.selectedItem as CategoryEntity
        val categoryId = category.id
        val categoryName = category.name
        val questionAmount = binding.tvQuestionsAmountValue.text.toString().toInt()
        val difficulty = binding.spinnerDifficulty.selectedItem.toString().lowercase()

        val bundle = Bundle()
        bundle.apply {
            putInt(CATEGORY_ID, categoryId)
            putString(CATEGORY_NAME, categoryName)
            putInt(QUESTIONS_AMOUNT, questionAmount)
            putString(DIFFICULTY, difficulty)
        }

        val navController =
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
        navController.navigate(R.id.action_startFragment_to_gameFragment, bundle)
    }

    override fun setupObservers() {
        viewModel.categoryList.flowWithLifecycle(
            lifecycle,
            Lifecycle.State.STARTED
        ).onEach {
            handleCategory(it)
        }.launchIn(lifecycleScope)
    }

    private fun handleCategory(list: List<CategoryEntity>) {
        val listWithAnyCategory: ArrayList<CategoryEntity> = ArrayList()
        listWithAnyCategory.add(CategoryEntity(-1, "Any Category"))
        list.forEach {
            listWithAnyCategory.add(it)
        }
        val adapter: ArrayAdapter<CategoryEntity> =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                listWithAnyCategory
            )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategory.adapter = adapter
    }

    override fun setupUi() {
        requireActivity().title = getString(R.string.quiz)
        setupSpinnerDifficulty()
    }

    private fun setupSpinnerDifficulty() {
        val listDifficulty: ArrayList<String> = ArrayList()
        listDifficulty.add("Any Difficulty")
        listDifficulty.add("Easy")
        listDifficulty.add("Medium")
        listDifficulty.add("Hard")
        val adapter: ArrayAdapter<String> =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                listDifficulty
            )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerDifficulty.adapter = adapter
    }

    companion object {
        const val CATEGORY_ID = "CATEGORY_ID"
        const val CATEGORY_NAME = "CATEGORY_NAME"
        const val QUESTIONS_AMOUNT = "QUESTIONS_AMOUNT"
        const val DIFFICULTY = "DIFFICULTY"
    }
}