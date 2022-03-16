package kg.geektech.quizappaziz.presentation.start

import android.util.Log
import android.widget.SeekBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kg.geektech.quizappaziz.databinding.FragmentStartBinding
import kg.geektech.quizappaziz.domain.common.base.BaseFragment
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
        Log.e("Aziz", "handleCategory: $list")
    }

    override fun setupUi() {
        requireActivity().title = "Quiz"
    }
}