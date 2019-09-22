package com.lebedevsd.githubviewer.ui.repocontributors

import androidx.lifecycle.SavedStateHandle
import com.lebedevsd.githubviewer.api.model.User
import com.lebedevsd.githubviewer.base.ui.BaseViewModel
import com.lebedevsd.githubviewer.di.ViewModelAssistedFactory
import com.lebedevsd.githubviewer.di.scheduler.BackgroundTaskScheduler
import com.lebedevsd.githubviewer.di.scheduler.UiScheduler
import com.lebedevsd.githubviewer.interactor.GetContributorsInteractor
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class RepoContributorsViewModel @AssistedInject constructor(
    @Assisted handle: SavedStateHandle,
    @UiScheduler val uiScheduler: Scheduler,
    @BackgroundTaskScheduler val bgScheduler: Scheduler,
    val getContributors: GetContributorsInteractor
) : BaseViewModel<RepoContributorsViewState>(handle) {

    private val subscription = CompositeDisposable()

    fun loadContributors(username: String, repoName: String) {
        subscription.addAll(
            this.getContributors(username, repoName)
                .doOnNext { notifyLoading(RepoContributorsViewState()) }
                .map {
                    it.map { user: User ->
                        ItemUser(
                            user.login,
                            user.avatar_url
                        )
                    }
                }
                .observeOn(uiScheduler)
                .subscribeOn(bgScheduler)
                .subscribe({
                    Timber.d(it.toString())
                    notifyContentLoaded(
                        RepoContributorsViewState(
                            users = it
                        )
                    )
                }, {
                    Timber.e(it.toString())
                    notifyError(it, RepoContributorsViewState())
                })
        )
    }

    override fun onCleared() {
        subscription.clear()
        super.onCleared()
    }

    @AssistedInject.Factory
    interface Factory : ViewModelAssistedFactory<RepoContributorsViewModel>
}
