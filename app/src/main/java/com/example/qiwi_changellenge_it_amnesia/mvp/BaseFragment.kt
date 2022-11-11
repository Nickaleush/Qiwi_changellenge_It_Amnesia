package com.example.qiwi_changellenge_it_amnesia.mvp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.qiwi_changellenge_it_Amnesia.BuildConfig
import com.example.qiwi_changellenge_it_amnesia.activity.MainActivity

import javax.inject.Inject

abstract class BaseFragment<P : BasePresenter> : Fragment(), MainActivity.OnBackPressedListener {

    @Inject
    lateinit var presenter: P
    private var isCommitsAllowed: Boolean = false
    private val resumeFragmentActions: MutableList<Runnable> by lazy { mutableListOf() }

    private var resultAction: Runnable? = null

    inline fun executeResumeFragmentAction(crossinline action: () -> Unit): Unit =
        executeResumeFragmentAction(Runnable { action.invoke() })

    fun executeResumeFragmentAction(action: Runnable) {
        if (isCommitsAllowed) action.run() else resumeFragmentActions.add(action)
    }

    fun executeResultAction(action: Runnable): Unit =
        if (isHidden.not()) action.run() else resultAction = action

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (BuildConfig.DEBUG) Log.e("_debug", "onCreate ${this.javaClass.simpleName}")
        createComponent()
    }

    override fun onPause() {
        isCommitsAllowed = false
        backPressedListener = null
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        isCommitsAllowed = true
        backPressedListener =this
        resumeFragmentActions.forEach(Runnable::run)
        resumeFragmentActions.clear()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden.not() && resultAction != null) {
            resultAction?.run()
            resultAction = null
        }
    }

    protected abstract fun createComponent()

    override fun onDestroyView() {
        if (BuildConfig.DEBUG)
            Log.e("_debug", "onDestroyView ${this.javaClass.simpleName}")
        presenter.stop()
        super.onDestroyView()
    }

    override fun onDestroy() {
        if (BuildConfig.DEBUG)
            Log.e("_debug", "onDestroy ${this.javaClass.simpleName}")
        presenter.dispose()
        super.onDestroy()
    }

    companion object{
        var backPressedListener: MainActivity.OnBackPressedListener? = null
    }
}