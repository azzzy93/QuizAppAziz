package kg.geektech.quizappaziz.presentation.history

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kg.geektech.quizappaziz.R
import kg.geektech.quizappaziz.databinding.FragmentHistoryBinding
import kg.geektech.quizappaziz.core.BaseFragment

class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {

    private lateinit var adapter: HistoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
    }

    private fun initAdapter() {
        adapter = HistoryAdapter()
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistory.adapter = adapter
        val list: ArrayList<HistoryEntity> = ArrayList()
        for (i in 0..10) {
            list.add(
                HistoryEntity(
                    i,
                    "Category: Mixed $i",
                    "Correct answers: $i/10",
                    "Difficulty: Easy $i",
                    "12 may 2019 20:32"
                )
            )
        }
        adapter.submitList(list)
    }

    override fun bind(): FragmentHistoryBinding {
        return FragmentHistoryBinding.inflate(layoutInflater)
    }

    override fun setupUi() {
        requireActivity().title = getString(R.string.history)
    }
}