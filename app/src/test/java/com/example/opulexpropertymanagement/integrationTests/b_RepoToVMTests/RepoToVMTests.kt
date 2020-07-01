package com.example.opulexpropertymanagement.integrationTests.b_RepoToVMTests

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import com.example.opulexpropertymanagement.util.ContentTestExtension
import com.example.opulexpropertymanagement.util.TEST_COROUTINE_DISPATCHER_KEY
import com.example.opulexpropertymanagement.util.TEST_COROUTINE_DISPATCHER_NAMESPACE
import com.example.tmcommonkotlin.logz
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

@ExtendWith(ContentTestExtension::class)
class RepoToVMTests (val testDispatcher: TestCoroutineDispatcher){
    @ParameterizedTest
    @MethodSource("FeedLoad")
    fun test1(test: Any) {
        logz("Hi")
    }
}