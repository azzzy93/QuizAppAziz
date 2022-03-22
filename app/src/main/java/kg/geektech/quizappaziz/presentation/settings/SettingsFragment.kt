package kg.geektech.quizappaziz.presentation.settings

import kg.geektech.quizappaziz.R
import kg.geektech.quizappaziz.databinding.FragmentSettingsBinding
import kg.geektech.quizappaziz.core.BaseFragment

class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {

    override fun bind(): FragmentSettingsBinding {
        return FragmentSettingsBinding.inflate(layoutInflater)
    }

    override fun setupUi() {
        requireActivity().title = getString(R.string.settings)
    }

}