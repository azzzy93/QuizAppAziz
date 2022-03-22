package kg.geektech.quizappaziz.presentation.game

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kg.geektech.quizappaziz.R
import kg.geektech.quizappaziz.databinding.ItemForBooleanBinding
import kg.geektech.quizappaziz.databinding.ItemForMultipleBinding
import kg.geektech.quizappaziz.domain.game.entity.QuestionsEntity

class GameAdapter() :
    ListAdapter<QuestionsEntity, RecyclerView.ViewHolder>(GameDiffCallback()) {

    var onItemClick: ((position: Int, correctAnswer: Int) -> Unit)? = null

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
            holderMultiple.onItemClick(question)
        } else {
            val holderBoolean = holder as ViewHolderBoolean
            holderBoolean.onBind(question)
            holderBoolean.onItemClick(question)
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

    inner class ViewHolderMultiple(private val binding: ItemForMultipleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(question: QuestionsEntity) {
            val list: ArrayList<String> = ArrayList()
            question.correctAnswer?.let { list.add(it) }
            question.incorrectAnswers?.let {
                it.forEach { string ->
                    list.add(string)
                }
            }
            list.shuffle()

            binding.tvQuestion.text = question.question
            binding.tvAnswer1.text = list[0]
            binding.tvAnswer2.text = list[1]
            binding.tvAnswer3.text = list[2]
            binding.tvAnswer4.text = list[3]

            val count = "${1 + adapterPosition}/${currentList.size}"
            binding.tvProgressCounter.text = count
            binding.progressLinear.max = currentList.size
            binding.progressLinear.progress = 1 + adapterPosition

            setEnabled(true)
            setAllBtnDefBg()
        }

        fun onItemClick(question: QuestionsEntity) {
            binding.tvAnswer1.setOnClickListener {
                it as TextView
                responseCheck(it, question)
                setEnabled(false)
            }
            binding.tvAnswer2.setOnClickListener {
                it as TextView
                responseCheck(it, question)
                setEnabled(false)
            }
            binding.tvAnswer3.setOnClickListener {
                it as TextView
                responseCheck(it, question)
                setEnabled(false)
            }
            binding.tvAnswer4.setOnClickListener {
                it as TextView
                responseCheck(it, question)
                setEnabled(false)
            }
        }

        private fun setEnabled(isEnabled: Boolean) {
            binding.tvAnswer1.isEnabled = isEnabled
            binding.tvAnswer2.isEnabled = isEnabled
            binding.tvAnswer3.isEnabled = isEnabled
            binding.tvAnswer4.isEnabled = isEnabled
        }

        private fun setAllBtnDefBg() {
            binding.tvAnswer1.setBackgroundResource(R.drawable.bg_for_answer_def)
            binding.tvAnswer2.setBackgroundResource(R.drawable.bg_for_answer_def)
            binding.tvAnswer3.setBackgroundResource(R.drawable.bg_for_answer_def)
            binding.tvAnswer4.setBackgroundResource(R.drawable.bg_for_answer_def)
        }

        private fun responseCheck(textView: TextView, question: QuestionsEntity) {
            textView.setBackgroundResource(R.drawable.bg_for_answer_enabled)
            if (textView.text == question.correctAnswer) {
                textView.setBackgroundResource(R.drawable.bg_for_answer_true)
                onItemClick?.invoke(adapterPosition, 1)
            } else {
                textView.setBackgroundResource(R.drawable.bg_for_answer_false)
                onItemClick?.invoke(adapterPosition, 0)
            }
        }
    }

    inner class ViewHolderBoolean(private val binding: ItemForBooleanBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(question: QuestionsEntity) {
            val list: ArrayList<String> = ArrayList()
            question.correctAnswer?.let { list.add(it) }
            question.incorrectAnswers?.let {
                it.forEach { string ->
                    list.add(string)
                }
            }
            list.shuffle()

            binding.tvQuestion.text = question.question
            binding.tvAnswerYes.text = list[0]
            binding.tvAnswerNo.text = list[1]

            val count = "${1 + adapterPosition}/${currentList.size}"
            binding.tvProgressCounter.text = count
            binding.progressLinear.max = currentList.size
            binding.progressLinear.progress = 1 + adapterPosition

            setEnabled(true)
            setAllBtnDefBg()
        }

        fun onItemClick(question: QuestionsEntity) {
            binding.tvAnswerYes.setOnClickListener {
                it as TextView
                responseCheck(it, question)
                setEnabled(false)
            }
            binding.tvAnswerNo.setOnClickListener {
                it as TextView
                responseCheck(it, question)
                setEnabled(false)
            }
        }

        private fun responseCheck(textView: TextView, question: QuestionsEntity) {
            textView.setBackgroundResource(R.drawable.bg_for_answer_enabled)
            if (textView.text == question.correctAnswer) {
                textView.setBackgroundResource(R.drawable.bg_for_answer_true)
                onItemClick?.invoke(adapterPosition, 1)
            } else {
                textView.setBackgroundResource(R.drawable.bg_for_answer_false)
                onItemClick?.invoke(adapterPosition, 0)
            }
        }

        private fun setEnabled(isEnabled: Boolean) {
            binding.tvAnswerYes.isEnabled = isEnabled
            binding.tvAnswerNo.isEnabled = isEnabled
        }

        private fun setAllBtnDefBg() {
            binding.tvAnswerYes.setBackgroundResource(R.drawable.bg_for_answer_def)
            binding.tvAnswerNo.setBackgroundResource(R.drawable.bg_for_answer_def)
        }
    }

    companion object {
        const val VIEW_TYPE_MULTIPLE = 100
        const val VIEW_TYPE_BOOLEAN = 101
    }
}