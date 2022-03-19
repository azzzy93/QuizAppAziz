package kg.geektech.quizappaziz.presentation.game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kg.geektech.quizappaziz.databinding.ItemForBooleanBinding
import kg.geektech.quizappaziz.databinding.ItemForMultipleBinding
import kg.geektech.quizappaziz.domain.game.entity.QuestionsEntity

class GameAdapter() :
    ListAdapter<QuestionsEntity, RecyclerView.ViewHolder>(GameDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_MULTIPLE) {
            val binding =
                ItemForMultipleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ViewHolderMultiple(binding)
        } else {
            val binding =
                ItemForBooleanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ViewHolderBoolean(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val question = getItem(position)
        if (holder.itemViewType == VIEW_TYPE_MULTIPLE) {
            val holderMultiple = holder as ViewHolderMultiple
            holderMultiple.onBind(question)
        } else {
            val holderBoolean = holder as ViewHolderBoolean
            holderBoolean.onBind(question)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val question = getItem(position)
        return if (question.type == "multiple") {
            VIEW_TYPE_MULTIPLE
        } else {
            VIEW_TYPE_BOOLEAN
        }
    }

    class ViewHolderMultiple(private val binding: ItemForMultipleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(question: QuestionsEntity) {
            binding.tvQuestion.text = question.question
            binding.tvAnswer1.text = question.correctAnswer
            question.incorrectAnswers?.let {
                binding.tvAnswer2.text = it[0]
                binding.tvAnswer3.text = it[1]
                binding.tvAnswer4.text = it[2]
            }
        }
    }

    class ViewHolderBoolean(private val binding: ItemForBooleanBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(question: QuestionsEntity) {
            binding.tvQuestion.text = question.question
            binding.tvAnswerYes.text = question.correctAnswer
            question.incorrectAnswers?.let {
                binding.tvAnswerNo.text = it[0]
            }
        }
    }

    companion object {
        const val VIEW_TYPE_MULTIPLE = 100
        const val VIEW_TYPE_BOOLEAN = 101
    }
}