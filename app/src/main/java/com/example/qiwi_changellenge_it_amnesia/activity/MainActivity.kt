package com.example.qiwi_changellenge_it_amnesia.activity

import android.os.Bundle
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
    }

    override fun onBackPressed() {
        if (BaseFragment.backPressedListener!=null) {
            BaseFragment.backPressedListener!!.onBackPressed()
        } else  {
            super.onBackPressed()
        }
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