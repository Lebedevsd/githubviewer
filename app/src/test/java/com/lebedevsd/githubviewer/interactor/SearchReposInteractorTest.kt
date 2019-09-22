package com.lebedevsd.githubviewer.interactor

import com.lebedevsd.githubviewer.api.model.ReposResponse
import com.lebedevsd.githubviewer.creator.testReposResponse
import com.lebedevsd.githubviewer.repository.SearchReposRepository
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class SearchReposInteractorTest {

    private val repository: SearchReposRepository = mockk()
    private val searchRepos = SearchReposInteractor(repository)

    @BeforeEach
    fun init() {
        clearMocks(repository)
    }

    @Test
    fun `should return response if repository returned any`() {
        val subscriber = TestSubscriber.create<ReposResponse>()
        val result: ReposResponse = testReposResponse()
        every { repository.searchRepos(any(), any()) } returns Flowable.just(result)

        searchRepos("", 0).subscribe(subscriber)

        subscriber.assertNoErrors()
        subscriber.assertValue {
            it == result
        }
    }

    @Test
    fun `should return error if repo has error`() {
        val subscriber = TestSubscriber.create<ReposResponse>()
        val error = RuntimeException("test")
        every { repository.searchRepos(any(), any()) } returns Flowable.error(error)

        searchRepos("", 0).subscribe(subscriber)

        subscriber.assertError(error)
        subscriber.assertNoValues()
    }
}