package kg.geektech.quizappaziz.presentation.game

import androidx.recyclerview.widget.DiffUtil
import kg.geektech.quizappaziz.domain.game.entity.QuestionsEntity

class GameDiffCallback : DiffUtil.ItemCallback<QuestionsEntity>() {

    override fun areItemsTheSame(oldItem: QuestionsEntity, newItem: QuestionsEntity) =
        oldItem.question == newItem.question

    override fun areContentsTheSame(oldItem: QuestionsEntity, newItem: QuestionsEntity) =
        oldItem == newItem
}