package com.jaydeepbhayani.league

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.jaydeepbhayani.league.databinding.ActivityMainBinding
import com.jaydeepbhayani.league.util.AppColor
import com.jaydeepbhayani.league.util.connectivity.ConnectivityObserver
import com.jaydeepbhayani.league.util.connectivity.NetworkConnectivityObserver
import com.jaydeepbhayani.league.util.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val navHostFragment by lazy { supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment }

    private val navController by lazy { navHostFragment.navController }

    private val connectivityObserver: NetworkConnectivityObserver by lazy {
        NetworkConnectivityObserver(this)
    }

    private var isFirst = true

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            connectivityObserver.observe().collect {
                checkInternet(it)
            }
        }
        inflateGraphAndSetStartDestination(R.id.postsFragment)
    }

    private fun inflateGraphAndSetStartDestination(startDestination: Int, args: Bundle? = null) {
        val graph = navController.navInflater.inflate(R.navigation.nav_graph)
            .apply { this.setStartDestination(startDestination) }
        navController.setGraph(graph, args)
    }

    private fun checkInternet(status: ConnectivityObserver.Status) {
        when (status) {
            ConnectivityObserver.Status.Available -> {
                if (!isFirst) {
                    binding.root.snackbar(
                        stringId = R.string.network_available,
                        drawableId = R.drawable.ic_round_check_circle_24,
                        color = AppColor.Success,
                        vibrate = true
                    )
                }
            }
            ConnectivityObserver.Status.Unavailable -> {
                binding.root.snackbar(
                    stringId = R.string.network_unavailable,
                    drawableId = R.drawable.ic_round_error_24,
                    color = AppColor.Error,
                    vibrate = true
                )
            }
            ConnectivityObserver.Status.Losing -> {
                binding.root.snackbar(
                    stringId = R.string.network_losing,
                    drawableId = R.drawable.ic_round_error_24,
                    color = AppColor.Yellow,
                    vibrate = true
                )
            }
            ConnectivityObserver.Status.Lost -> {
                isFirst = true
                binding.root.snackbar(
                    stringId = R.string.network_lost,
                    drawableId = R.drawable.ic_round_error_24,
                    color = AppColor.Error,
                    vibrate = true
                )
            }
        }
    }

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }
}