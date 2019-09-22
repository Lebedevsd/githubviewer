package com.lebedevsd.githubviewer.ui.searchrepos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.lebedevsd.githubviewer.api.model.Repo
import com.lebedevsd.githubviewer.base.ui.BaseViewModel
import com.lebedevsd.githubviewer.di.ViewModelAssistedFactory
import com.lebedevsd.githubviewer.di.scheduler.BackgroundTaskScheduler
import com.lebedevsd.githubviewer.di.scheduler.UiScheduler
import com.lebedevsd.githubviewer.interactor.SearchReposInteractor
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.processors.BehaviorProcessor
import timber.log.Timber

class SearchReposViewModel @AssistedInject constructor(
    @Assisted handle: SavedStateHandle,
    @UiScheduler val uiScheduler: Scheduler,
    @BackgroundTaskScheduler val bgScheduler: Scheduler,
    val searchRepos: SearchReposInteractor
) : BaseViewModel<SearchReposViewState>(handle) {

    val navigateToRepo: LiveData<NavigationData>
        get() = _navigateToRepo
    private val _navigateToRepo = MutableLiveData<NavigationData>()

    private val subscription = CompositeDisposable()

    private val query = BehaviorProcessor.create<String>()
    private val page = BehaviorProcessor.create<Int>()

    init {
        Timber.d("Initializing")
        val query: String = "TEST"//? = handle[STATE_QUERY]
        query?.let { this.query.onNext(it) }
        page.onNext(handle[STATE_PAGE] ?: 0)

        initSub()
    }

    fun nextPage(page: Int) {
        handle[STATE_PAGE] = page
        this.page.onNext(page)
    }

    fun search(query: String) {
        handle[STATE_QUERY] = query
        handle[STATE_PAGE] = 0
        this.page.onNext(0)
        this.query.onNext(query)
    }

    private fun initSub() {
        subscription.addAll(

            Flowable.combineLatest(
                page,
                query,
                BiFunction<Int, String, Pair<Int, String>> { page, query -> Pair(page, query) }
            ).flatMap {
                notifyLoading(
                    SearchReposViewState(
                        it.second,
                        it.first
                    )
                )
                searchRepos(it.second)
            }
                .map {
                    it.map { repo: Repo ->
                        ItemRepo(
                            repo.full_name,
                            repo.name,
                            repo.owner.login,
                            repo.owner.avatar_url,
                            repo.language,
                            repo.forks_count.toString()
                        )
                    }
                }
                .observeOn(uiScheduler)
                .subscribeOn(bgScheduler)
                .subscribe({
                    Timber.d(it.toString())
                    notifyContentLoaded(
                        SearchReposViewState(
                            query = query.blockingFirst(),
                            repos = it,
                            onClick = _navigateToRepo::setValue
                        )
                    )
                }, {
                    Timber.e(it.toString())
                    notifyError(it, SearchReposViewState())
                })
        )
    }

    override fun onCleared() {
        subscription.clear()
        super.onCleared()
    }

    companion object {
        private const val STATE_PAGE = "STATE_PAGE"
        private const val STATE_QUERY = "STATE_QUERY"
    }

    @AssistedInject.Factory
    interface Factory : ViewModelAssistedFactory<SearchReposViewModel>
}
