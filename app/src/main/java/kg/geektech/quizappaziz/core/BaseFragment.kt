package kg.geektech.quizappaziz.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.viewbinding.ViewBinding
import kg.geektech.quizappaziz.R

abstract class BaseFragment<VB : ViewBinding>() : Fragment() {

    protected lateinit var binding: VB
    protected abstract fun bind(): VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bind()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
        setupObservers()
        setupListeners()
    }

    open fun setupListeners() {}

    open fun setupObservers() {}

    open fun setupUi() {}

    protected fun navigateFragment(fragmentId: Int? = null, bundle: Bundle? = null) {
        val navController =
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
        if (fragmentId != null && bundle != null) {
            navController.navigate(fragmentId, bundle)
        } else if (fragmentId != null && bundle == null) {
            navController.navigate(fragmentId)
        } else {
            navController.navigateUp()
        }
    }
}