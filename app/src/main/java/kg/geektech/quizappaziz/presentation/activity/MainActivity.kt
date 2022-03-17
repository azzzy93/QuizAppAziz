package kg.geektech.quizappaziz.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import dagger.hilt.android.AndroidEntryPoint
import kg.geektech.quizappaziz.R
import kg.geektech.quizappaziz.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActionBar()
        initNavController()
    }

    private fun initActionBar() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_back)
        setSupportActionBar(binding.toolbar)
    }

    private fun initNavController() {
        navController = Navigation.findNavController(this, R.id.fragmentContainerView)
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.startFragment ||
                destination.id == R.id.historyFragment ||
                destination.id == R.id.settingsFragment
            ) {
                binding.bottomNavigationView.isVisible = true
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            } else {
                binding.bottomNavigationView.isVisible = false
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        }
    }
}