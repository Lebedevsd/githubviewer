package com.lebedevsd.githubviewer.ui.searchrepos

import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.bumptech.glide.load.engine.Resource
import com.lebedevsd.githubviewer.api.model.ReposResponse
import com.lebedevsd.githubviewer.creator.testRepo
import com.lebedevsd.githubviewer.creator.testReposResponse
import com.lebedevsd.githubviewer.extension.InstantExecutorExtension
import com.lebedevsd.githubviewer.interactor.SearchReposInteractor
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
internal class SearchReposViewModelTest {

    var observer: Observer<Resource<ReposResponse>> = mockk(relaxed = true)
    val handle: SavedStateHandle = mockk(relaxed = true)
    val reposInteractor: SearchReposInteractor = mockk(relaxed = true)

    init {

    }

    lateinit var viewModel: SearchReposViewModel

    @BeforeEach
    fun init() {
        clearMocks(handle, reposInteractor)
        every<Int?> { handle["STATE_PAGE"] } returns null
        every<String?> { handle["STATE_QUERY"] } returns null
        viewModel = SearchReposViewModel(
            handle,
            Schedulers.trampoline(),
            Schedulers.trampoline(),
            reposInteractor
        )
    }

    @Test
    fun `should show data`() {
        val response = testReposResponse()
        every { reposInteractor(any(), any()) } returns Flowable.just(response)
        viewModel.lce.observeForever { observer }

        viewModel.lce.value!!.content.itemSearch.onQueryTextListener?.invoke("test")

        assert(viewModel.lce.value!!.content.itemSearch.query == "test")
    }

    @Test
    fun `should load second page if there are more data`() {
        val response1 = testReposResponse(incomplete_results = false, items = arrayListOf(testRepo(id = 123)))
        val response2 = testReposResponse(incomplete_results = false, items = arrayListOf(testRepo(id = 321)))
        every { reposInteractor(any(), any()) } returnsMany  listOf(Flowable.just(response1), Flowable.just(response2))
        viewModel.lce.observeForever { observer }
        viewModel.lce.value!!.content.itemSearch.onQueryTextListener?.invoke("test")

        viewModel.nextPage(2)

        assert(viewModel.lce.value!!.content.itemSearch.query == "test")
        assert(viewModel.lce.value!!.content.reposState.repos.size == 2)
    }
}