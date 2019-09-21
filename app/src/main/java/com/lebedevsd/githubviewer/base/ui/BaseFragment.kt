package com.lebedevsd.githubviewer.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.lebedevsd.githubviewer.di.ViewModelFactory
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

abstract class BaseFragment<S, T : BaseViewModel<S>, B : ViewDataBinding> : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    protected val viewModel: T by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)
    }

    protected lateinit var binding: B

    protected abstract val viewModelClass: Class<T>

    @get:LayoutRes
    protected abstract val layoutId: Int

    private var errorSnackBar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.lce.observe(this, Observer { state ->
            if (state is LCE.Error) {
                showError(state.throwable)
            } else {
                dismissErrorIfShown()
            }
            Timber.d("Updating controller")
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, viewModel)
        return binding.root
    }

    private fun showError(error: Throwable) {
//        errorSnackBar = Snackbar.make(binding.root, R.string.no_internet_connection, Snackbar.LENGTH_INDEFINITE).apply {
//            show()
//        }
    }

    private fun dismissErrorIfShown() {
//        errorSnackBar?.dismiss()
    }
}
