package com.example.currencycalculatorapp.presentation.activity

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.example.currencycalculator.R
import com.example.currencycalculator.databinding.ActivityMainBinding
import com.example.currencycalculatorapp.presentation.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun inflateViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController.apply { setGraph(R.navigation.nav_graph) }
    }
}