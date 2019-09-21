package com.lebedevsd.githubviewer.ui.main

import androidx.lifecycle.SavedStateHandle
import com.lebedevsd.githubviewer.base.ui.BaseViewModel
import com.lebedevsd.githubviewer.di.ViewModelAssistedFactory
import com.lebedevsd.githubviewer.di.scheduler.BackgroundTaskScheduler
import com.lebedevsd.githubviewer.di.scheduler.UiScheduler
import com.lebedevsd.githubviewer.interactor.SearchReposInteractor
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class SearchReposViewModel @AssistedInject constructor(
    @Assisted handle: SavedStateHandle,
    @UiScheduler val uiScheduler: Scheduler,
    @BackgroundTaskScheduler val bgScheduler: Scheduler,
    val searchRepos: SearchReposInteractor
) : BaseViewModel<SearchReposViewState>(handle) {

    private val subscription = CompositeDisposable()

    init {
        Timber.d("Initializing")
        val page = handle[STATE_PAGE] ?: 0
        loadData(page)
    }

    private fun loadData(page: Int) {
        handle[STATE_PAGE] = page
        subscription.addAll(
            searchRepos("android")
                .observeOn(uiScheduler)
                .subscribeOn(bgScheduler)
                .subscribe({ Timber.d(it.toString()) }, { Timber.d(it.toString()) })
        )

    }

    override fun onCleared() {
        subscription.clear()
        super.onCleared()
    }

    companion object {
        private const val STATE_PAGE = "STATE_PAGE"
    }

    @AssistedInject.Factory
    interface Factory : ViewModelAssistedFactory<SearchReposViewModel>
}
