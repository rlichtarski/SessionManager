package com.rradzzio.sessionmanager.presentation.ui.auth

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.rradzzio.sessionmanager.R
import com.rradzzio.sessionmanager.databinding.ActivityAuthBinding
import com.rradzzio.sessionmanager.presentation.ui.BaseActivity
import com.rradzzio.sessionmanager.util.Status
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class AuthActivity : BaseActivity(), NavController.OnDestinationChangedListener{

    private lateinit var navController: NavController

    private lateinit var binding: ActivityAuthBinding

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.auth_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        subscribeObservers()
    }

    private fun subscribeObservers() {
        authViewModel.loginResult.observe(this, { event ->
            event.getContentIfNotHandled()?.let { result ->
                result.let {
                    when(result.status) {

                        Status.LOADING -> {
                            displayProgressBar(true)
                            Timber.d("loading...")
                        }

                        Status.SUCCESS -> {
                            displayProgressBar(false)
                            Timber.d("auth token: ${result.data!!.token}")
                        }

                        Status.ERROR -> {
                            displayProgressBar(false)
                            Timber.e("error...")
                            Timber.e(result.message)
                        }

                    }
                }
            }

        })
    }

    override fun displayProgressBar(loading: Boolean) {
        binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        //not implemented yet
    }
}