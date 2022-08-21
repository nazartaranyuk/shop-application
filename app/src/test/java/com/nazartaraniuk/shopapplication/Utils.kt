package com.nazartaraniuk.shopapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * @param trigger Lambda that should trigger value emit
 * @param testBlock Lambda where the first emitted value (or null if nothing has been emitted) passed
 */
inline fun <T> LiveData<T>.testFirstValueEmitted(
    trigger: () -> Unit,
    testBlock: (wasChanged: Boolean, newValue: T?) -> Unit
) {
    // Setup
    var result: T? = null
    var wasChanged = false
    val observer = Observer<T> {
        result = it
        wasChanged = true
    }
    observeForever(observer)

    // When
    trigger()

    // Then
    testBlock(wasChanged, result)

    // Clean-up
    removeObserver(observer)
}

@ExperimentalCoroutinesApi
class MainCoroutineScopeRule(
    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}