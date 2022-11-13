package com.example.qiwi_changellenge_it_amnesia.ui.activity

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.qiwi_changellenge_it_amnesia.App
import com.example.qiwi_changellenge_it_Amnesia.R
import com.example.qiwi_changellenge_it_amnesia.mvp.BaseActivity
import com.example.qiwi_changellenge_it_amnesia.mvp.BaseFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity<MainPresenterImpl>(), MainView {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.view = this
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        val mainGraph = navController.navInflater.inflate(R.navigation.navigation_graph)
        navController.graph = mainGraph
        bottomNavigationView = findViewById(R.id.mainBottomNavigationView)
        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.authFragment -> hideBotNav()
                else -> showBotNav()
            }
        }
    }

    override fun onBackPressed() {
        if (BaseFragment.backPressedListener != null) {
            BaseFragment.backPressedListener!!.onBackPressed()
        } else {
            super.onBackPressed()
        }
    }

    private fun hideBotNav() {
        bottomNavigationView.animate().alpha(0.0f).duration = 300
        bottomNavigationView.visibility = View.GONE
    }

    private fun showBotNav() {
        bottomNavigationView.animate()
            .alpha(1.0f).duration = 1000
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        bottomNavigationView.visibility = View.VISIBLE
    }

    interface OnBackPressedListener {
        fun onBackPressed()
    }

    override fun createComponent() {
        App.instance
            .getAppComponent()
            .createMainActivity()
            .inject(this)
    }
}