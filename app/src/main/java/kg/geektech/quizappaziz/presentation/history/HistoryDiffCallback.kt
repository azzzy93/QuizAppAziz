package kg.geektech.quizappaziz.presentation.history

import androidx.recyclerview.widget.DiffUtil

class HistoryDiffCallback : DiffUtil.ItemCallback<HistoryEntity>() {

    override fun areItemsTheSame(oldItem: HistoryEntity, newItem: HistoryEntity) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: HistoryEntity, newItem: HistoryEntity) =
        oldItem == newItem
}