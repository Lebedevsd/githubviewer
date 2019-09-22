package com.lebedevsd.githubviewer.ui.repocontributors

import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.bumptech.glide.load.engine.Resource
import com.lebedevsd.githubviewer.api.model.User
import com.lebedevsd.githubviewer.creator.testUser
import com.lebedevsd.githubviewer.extension.InstantExecutorExtension
import com.lebedevsd.githubviewer.interactor.GetContributorsInteractor
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
internal class RepoContributorsViewModelTest{

    var observer: Observer<Resource<List<User>>> = mockk(relaxed = true)
    val handle: SavedStateHandle = mockk()
    val getContributors: GetContributorsInteractor = mockk(relaxed = true)

    val viewModel = RepoContributorsViewModel(
        handle,
        Schedulers.trampoline(),
        Schedulers.trampoline(),
        getContributors
    )

    @BeforeEach
    fun init() {
        clearMocks(handle, getContributors)
    }

    @Test
    fun `should show data`(){
        val collaborators = listOf(testUser())
        every { getContributors(any(), any())} returns Flowable.just(collaborators)
        viewModel.lce.observeForever { observer }

        viewModel.loadContributors("", "")

        assert(viewModel.lce.value!!.content.users.size == collaborators.size)
    }
}