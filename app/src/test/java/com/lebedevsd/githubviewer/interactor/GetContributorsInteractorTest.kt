package com.lebedevsd.githubviewer.interactor

import com.lebedevsd.githubviewer.api.model.User
import com.lebedevsd.githubviewer.creator.testUser
import com.lebedevsd.githubviewer.repository.ContributorsRepository
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GetContributorsInteractorTest {

    private val repository: ContributorsRepository = mockk()
    private val getContributors = GetContributorsInteractor(repository)

    @BeforeEach
    fun init() {
        clearMocks(repository)
    }

    @Test
    fun `should return empty list if repo has no items`() {
        val subscriber = TestSubscriber.create<List<User>>()
        val result: List<User> = emptyList()
        every { repository.getContributors(any(), any()) } returns Flowable.just(result)

        getContributors("", "").subscribe(subscriber)

        subscriber.assertNoErrors()
        subscriber.assertValue {
            it == result
        }
    }

    @Test
    fun `should return actual list if repo has items`() {
        val subscriber = TestSubscriber.create<List<User>>()
        val result: List<User> = listOf(testUser())
        every { repository.getContributors(any(), any()) } returns Flowable.just(result)

        getContributors("", "").subscribe(subscriber)

        subscriber.assertNoErrors()
        subscriber.assertValue {
            it == result
        }
    }

    @Test
    fun `should return error if repo has error`() {
        val subscriber = TestSubscriber.create<List<User>>()
        val error = RuntimeException("test")
        every { repository.getContributors(any(), any()) } returns Flowable.error(error)

        getContributors("", "").subscribe(subscriber)

        subscriber.assertError(error)
        subscriber.assertNoValues()
    }
}