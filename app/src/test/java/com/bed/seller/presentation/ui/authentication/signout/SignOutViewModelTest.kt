package com.bed.seller.presentation.ui.authentication.signout

import org.junit.Test
import org.junit.Rule
import org.junit.Before
import org.junit.runner.RunWith

import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.assertEquals

import org.mockito.Mock
import org.mockito.kotlin.whenever
import org.mockito.kotlin.doAnswer
import org.mockito.stubbing.Answer
import org.mockito.junit.MockitoJUnitRunner

import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.ExperimentalCoroutinesApi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule

import com.bed.seller.presentation.commons.states.States

import com.bed.core.usecases.authentication.SignOutUseCase

import com.bed.test.rule.MainCoroutineRule
import com.bed.test.factories.authentication.AuthenticationFactory

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
internal class SignOutViewModelTest {
    @get:Rule
    val rule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var useCase: SignOutUseCase

    private lateinit var viewModel: SignOutViewModel

    private lateinit var states: MutableList<States<Unit>>

    @Before
    fun setUp() {
        states = mutableListOf()
        viewModel = SignOutViewModel(useCase)
    }

    @Test
    fun `Should issue first loading state when trying to sign out account with successful return`() = runTest {
        whenever(useCase()).doAnswer(Answer {  })

        val job = launch(rule.dispatcher.main()) { viewModel.state.toList(states) }

        viewModel.signOut()

        assertEquals(states[AuthenticationFactory.INITIAL], States.Initial)
        assertEquals(states[AuthenticationFactory.LOADING], States.Loading)
        assertTrue(states[AuthenticationFactory.SUCCESS] is States.Success)

        job.cancel()
    }
}
