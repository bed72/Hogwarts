package com.bed.seller.presentation.ui.authentication.signout

import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals

import org.mockito.Mock
import org.mockito.kotlin.whenever
import org.mockito.junit.MockitoJUnitRunner

import kotlinx.coroutines.test.runTest

import com.bed.core.usecases.authentication.SignOutUseCase

@RunWith(MockitoJUnitRunner::class)
internal class SignOutViewModelTest {
    @Mock
    private lateinit var useCase: SignOutUseCase

    private lateinit var viewModel: SignOutViewModel

    @Before
    fun setUp() {
        viewModel = SignOutViewModel(useCase)
    }

    @Test
    fun `Should return true in IsSignOut State when trying sign out with return success`() =
        runTest {
            whenever(useCase()).thenReturn(true)

            viewModel.signOut()

            val (success) = viewModel.state.value as SignOutViewModel.States.IsSignOut
            assertEquals(true, success)
        }

    @Test
    fun `Should return false in IsSignOut State when trying sign out with return failure`() =
        runTest {
            whenever(useCase()).thenReturn(false)

            viewModel.signOut()

            val (success) = viewModel.state.value as SignOutViewModel.States.IsSignOut
            assertEquals(false, success)
        }
}
