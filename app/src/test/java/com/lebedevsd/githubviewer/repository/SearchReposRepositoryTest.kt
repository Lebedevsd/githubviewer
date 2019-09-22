package com.lebedevsd.githubviewer.repository

import com.lebedevsd.githubviewer.api.GithubApi
import com.lebedevsd.githubviewer.api.model.ReposResponse
import com.lebedevsd.githubviewer.creator.testReposResponse
import io.mockk.*
import io.reactivex.Single
import io.reactivex.subscribers.TestSubscriber
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class SearchReposRepositoryTest {
    val remoteApi: GithubApi = mockk()
    val repository = SearchReposRepository(remoteApi)

    @BeforeEach
    fun init() {
        clearMocks(remoteApi)
    }

    @Nested
    inner class SearchRepos {

        @Test
        fun `should return items`() {
            val testObserver = TestSubscriber.create<ReposResponse>()
            val result = testReposResponse()
            every { remoteApi.searchRepos(any(), any()) } returns Single.just(result)

            repository.searchRepos("", 0).subscribe(testObserver)

            verify { remoteApi.searchRepos(any(), any()) }
            testObserver.assertNoErrors()
            testObserver.assertValue { it == result }
        }

        @Test
        fun `should return error if error in api`() {
            val testObserver = TestSubscriber.create<ReposResponse>()
            val error = RuntimeException("test")
            every { remoteApi.searchRepos(any(), any()) } returns Single.error(error)

            repository.searchRepos("", 0).subscribe(testObserver)

            verifyOrder {
                remoteApi.searchRepos(any(), any())
            }
            testObserver.assertError(error)
            testObserver.assertNoValues()
        }

    }
}