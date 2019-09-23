package com.lebedevsd.githubviewer.ui.searchrepos

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import com.lebedevsd.githubviewer.api.model.Repo
import com.lebedevsd.githubviewer.base.livedata.SingleLiveEvent
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
    private val _navigateToRepo = SingleLiveEvent<NavigationData>()

    private val subscription = CompositeDisposable()

    private val query = BehaviorProcessor.create<String>()
    private val page = BehaviorProcessor.create<Int>()

    init {
        Timber.d("Initializing")
        val page = handle[STATE_PAGE] ?: 1
        this.page.onNext(page)

        val query: String = handle[STATE_QUERY] ?: ""
        if (query.isNotEmpty()) {
            this.query.onNext(query)
        }

        notifyContentLoaded(
            SearchReposViewState(
                page = page,
                onClick = _navigateToRepo::setValue,
                itemSearch = ItemSearch(query, this::search)
            )
        )

        initSub()
    }

    fun nextPage(page: Int) {
        if (lce.value!!.content.reposState.hasNext) {
            handle[STATE_PAGE] = page
            this.page.onNext(page)
        }
    }

    private fun search(query: String) {
        subscription.clear()
        handle[STATE_QUERY] = query
        handle[STATE_PAGE] = 1
        this.page.onNext(1)
        this.query.onNext(query)

        val content = lce.value!!.content
        notifyLoading(
            content.copy(
                itemSearch = content.itemSearch.copy(
                    query = query
                ),
                page = 1,
                reposState = ReposState()
            )
        )
        initSub()
    }

    private fun initSub() {
        subscription.addAll(

            Flowable.combineLatest(
                page,
                query,
                BiFunction<Int, String, Pair<Int, String>> { page, query -> Pair(page, query) }
            )
                .observeOn(bgScheduler)
                .flatMap { pair ->
                val content = lce.value!!.content
                notifyLoading(
                    content.copy(
                        itemSearch = content.itemSearch.copy(
                            query = pair.second
                        ),
                        page = pair.first
                    )
                )
                searchRepos(pair.second, pair.first)
            }
                .map {
                    Pair(
                        it.items.map { repo: Repo ->
                            ItemRepo(
                                repo.full_name,
                                repo.name,
                                repo.owner.login,
                                repo.owner.avatar_url,
                                repo.language ?: "",
                                repo.forks_count.toString()
                            )
                        }, !it.incomplete_results
                    )
                }
                .observeOn(uiScheduler)
                .subscribeOn(bgScheduler)
                .subscribe({
                    Timber.d(it.toString())
                    val content = lce.value!!.content
                    val mergedRepos = content.reposState.repos.toMutableList()
                    mergedRepos.addAll(it.first)
                    notifyContentLoaded(
                        content.copy(
                            reposState = content.reposState.copy(
                                repos = mergedRepos,
                                hasNext = it.second
                            )
                        )
                    )
                }, {
                    Timber.e(it.toString())
                    notifyError(it, lce.value!!.content)
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
