package com.melvin.ongandroid.view.activity

import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import com.melvin.ongandroid.databinding.ActivityMainBinding
import com.melvin.ongandroid.utils.CheckNetworkConnection
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var checkNetworkConnection: CheckNetworkConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun callNetWorkConnection() {
        checkNetworkConnection = CheckNetworkConnection(application)
        checkNetworkConnection.observe(this) { isConnected ->
            if (isConnected) {
                binding.fragmentContainerView.visibility = VISIBLE
                binding.cNotInternet.visibility = GONE
            } else {
                binding.fragmentContainerView.visibility = GONE
                binding.cNotInternet.visibility = VISIBLE

            }
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)

        return (capabilities != null && capabilities.hasCapability(NET_CAPABILITY_INTERNET))
    }


    private fun drawLayout() {
        if (isNetworkAvailable()) {
            binding.fragmentContainerView.visibility = VISIBLE
            binding.cNotInternet.visibility = GONE
        } else {
            binding.fragmentContainerView.visibility = GONE
            binding.cNotInternet.visibility = VISIBLE
        }
    }
}