package com.rradzzio.sessionmanager.presentation.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.rradzzio.sessionmanager.R
import com.rradzzio.sessionmanager.databinding.ActivityAuthBinding
import com.rradzzio.sessionmanager.domain.models.ResponseType
import com.rradzzio.sessionmanager.domain.models.StateResponse
import com.rradzzio.sessionmanager.presentation.ui.BaseActivity
import com.rradzzio.sessionmanager.presentation.ui.displayErrorDialog
import com.rradzzio.sessionmanager.presentation.ui.displayToast
import com.rradzzio.sessionmanager.presentation.ui.main.MainActivity
import com.rradzzio.sessionmanager.util.Status
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class AuthActivity : BaseActivity() {

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
        checkPreviousAuthUser()
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
                            result.data?.let { authToken ->
                                sessionManager.login(authToken)
                            }
                            Timber.d("auth token: ${result.data!!.token}")
                        }

                        Status.ERROR -> {
                            displayProgressBar(false)
                            Timber.e("error...")
                            Timber.e(result.message)
                            result.data?.let {
                                it.errorResponse?.let { stateResponse ->
                                    handleErrorResponse(stateResponse)
                                }
                            }
                        }

                    }
                }
            }

        })

        sessionManager.cachedToken.observe(this, { authToken ->
            if(authToken?.token != null) {
                Timber.d("NAVIGATING TO MAIN ACTIVITY")
                navMainActivity()
            }
        })
    }

    private fun navMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun checkPreviousAuthUser() {
        authViewModel.checkPreviousAuthUser()
    }

    private fun handleErrorResponse(response: StateResponse) {
        when(response.errorResponseType) {

            is ResponseType.Toast -> {
                Timber.d("handleErrorResponse Toast type: ${response.message}")
                response.message?.let {
                    displayToast(it)
                }
            }

            is ResponseType.Dialog -> {
                Timber.d("handleErrorResponse Dialog type: ${response.message}")
                response.message?.let {
                    displayErrorDialog(it)
                }
            }

            is ResponseType.None -> {
                Timber.i("handleErrorResponse: ${response.message}")
            }

        }
    }

    override fun displayProgressBar(loading: Boolean) {
        binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
    }

}