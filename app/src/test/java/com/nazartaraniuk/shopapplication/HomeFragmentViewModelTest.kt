package com.nazartaraniuk.shopapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nazartaraniuk.shopapplication.domain.usecases.GetHomePageUseCase
import com.nazartaraniuk.shopapplication.presentation.adapters.DisplayableItem
import com.nazartaraniuk.shopapplication.presentation.home_screen.HomeFragmentViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import java.lang.Exception

@ExperimentalCoroutinesApi
class HomeFragmentViewModelTest {

    private val getHomePageUseCase = mockk<GetHomePageUseCase>(relaxed = true)

    @get:Rule
    var ruleChain: TestRule = RuleChain.outerRule(InstantTaskExecutorRule())
        .around(MainCoroutineScopeRule())

    @Test
    fun `test success get data`() = runTest() {

        coEvery { getHomePageUseCase() } returns flow {
            emit(Result.success(emptyList()))
        }

        val viewModel = HomeFragmentViewModel(getHomePageUseCase)
        viewModel.loadingState.testFirstValueEmitted(
            trigger = {},
            testBlock = { wasChanged, _ ->
                assertTrue(wasChanged)
            }
        )
    }

    @Test
    fun `test error get data`() = runTest {
        coEvery { getHomePageUseCase() } returns flow {
            emit(Result.failure(Exception("Test")))
        }
        val viewModel = HomeFragmentViewModel(getHomePageUseCase)
        viewModel.errorAction.testFirstValueEmitted(
            trigger = {},
            testBlock = { wasChanged, _ ->
                assertTrue(wasChanged)
            }
        )
    }

}