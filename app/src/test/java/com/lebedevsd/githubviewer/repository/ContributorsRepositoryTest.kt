package com.lebedevsd.githubviewer.repository

import com.lebedevsd.githubviewer.api.GithubApi
import com.lebedevsd.githubviewer.api.model.User
import com.lebedevsd.githubviewer.creator.testUser
import io.mockk.*
import io.reactivex.Single
import io.reactivex.subscribers.TestSubscriber
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ContributorsRepositoryTest {

    val remoteApi: GithubApi = mockk()
    val repository = ContributorsRepository(remoteApi)

    @BeforeEach
    fun init() {
        clearMocks(remoteApi)
    }

    @Nested
    inner class GetContributors {

        @Test
        fun `should return items`() {
            val testObserver = TestSubscriber.create<List<User>>()
            val result = listOf(testUser())
            every { remoteApi.getContributors(any(), any()) } returns Single.just(result)

            repository.getContributors("", "").subscribe(testObserver)

            verify { remoteApi.getContributors(any(), any()) }
            testObserver.assertNoErrors()
            testObserver.assertValue { it == result }
        }

        @Test
        fun `should return error if error in api`() {
            val testObserver = TestSubscriber.create<List<User>>()
            val error = RuntimeException("test")
            every { remoteApi.getContributors(any(), any()) } returns Single.error(error)

            repository.getContributors("", "").subscribe(testObserver)

            verifyOrder {
                remoteApi.getContributors(any(), any())
            }
            testObserver.assertError(error)
            testObserver.assertNoValues()
        }

    }
}