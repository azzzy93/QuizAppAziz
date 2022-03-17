package kg.geektech.quizappaziz.presentation.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kg.geektech.quizappaziz.databinding.ItemHistoryBinding

class HistoryAdapter() :
    ListAdapter<HistoryEntity, HistoryAdapter.ViewHolder>(HistoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = getItem(position)
        holder.onBind(history)
    }

    class ViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(history: HistoryEntity) {
            binding.tvCategoryName.text = history.category
            binding.tvCorrectAnswers.text = history.correctAnswer
            binding.tvDifficulty.text = history.difficulty
            binding.tvDate.text = history.date
        }

    }
}